var activeUuid = '';
var actionUrl = '/rooms';
var notificationMessage = 'Operation is successful';

$(document).on('ready', function () {
    initializeDataTables();
    bindUIElementEvents();
});

function initializeDataTables() {
    $("#room-table").DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "rooms/getRoomForDataTable",
            "type": "POST",
            "contentType": "application/json;charset=utf-8",
            "data": function(data) {
                return JSON.stringify(data);
            }
        },
        "columns": [{
            "data": "uuid",
            "className": "hide-column"
        },{
            "data": "roomNumber"
        },{
            "data": "dateCreated"
        },{
            "data": "dateLastModified"
        },{
            "data": null,
            "orderable": false,
            "searchable": false,
            "defaultContent":
                '<button type="button" class="btn btn-block btn-primary btn-sm edit-button">Edit</button>' +
                '<button type="button" class="btn btn-block btn-danger btn-sm delete-button">Delete</button>'
        }],
        "autoWidth": false
    });
}

function bindUIElementEvents() {
    $(document).on('click', '.add-button', function() {
        $('#modal-form .modal-title').html('Add Room');
        activeUuid = '';
        actionUrl = '/rooms/createRoom';
        notificationMessage = 'Successfully added new room';
    });

    $(document).on('click', '.edit-button', function() {
        $('#modal-form .modal-title').html('Edit Room');
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/rooms/updateRoom';
        notificationMessage = 'Successfully updated room';

        $('#modal-form').modal('show');
        $.ajax({
            'url': '/rooms/getRoom',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({'uuid': activeUuid}),
            'success': function(room) {
                $('#room-number').val(room.roomNumber);
            }
        });
    });

    $(document).on('click', '.delete-button', function() {
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/rooms/deleteRoom';
        notificationMessage = 'Successfully deleted room';
    });

    $('#modal-form').on('hidden.bs.modal', function(e) {
        clearFormWarnings();
        clearFormValues();
    });

    $('#modal-form .save-button').on('click', function() {
        clearFormWarnings();

        var room = {
            'uuid': activeUuid,
            'roomNumber': $('#room-number').val()
        };

        var validationStatus = validateRoom(room);
        if(validationStatus.status == "SUCCESS") {
            $.ajax({
                'url': actionUrl,
                'type': 'POST',
                'contentType': 'application/json;charset=utf-8',
                'data': JSON.stringify(room),
                'success': function(validationStatus) {
                    if(validationStatus.status == 'SUCCESS') {
                        $('#modal-form').modal('hide');
                        showNotificationModal(notificationMessage);
                        $('#room-table').DataTable().draw();
                    } else if(validationStatus.status == 'FAILED') {
                        showValidationStatus(validationStatus);
                    }
                }
            });
        } else {
            showValidationStatus(validationStatus);
        }
    });
}

function clearFormValues() {
    $('#room-number').val("");
}

function validateRoom(room) {
    var validationStatus = {"status": "SUCCESS", "errorCodeList": []};

    if(!room.roomNumber) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("ROOM_ROOM_NUMBER_MISSING");
    } else if(room.roomNumber.length > 150) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("ROOM_ROOM_NUMBER_TOO_LONG");
    }

    return validationStatus;
}

function showValidationStatus(validationStatus) {
    if(validationStatus.errorCodeList.indexOf('ROOM_ROOM_NUMBER_MISSING') > -1) {
        $('#room-number').parent().find('.help-block').css('display', 'block');
        $('#room-number').parent().find('.help-block').html('Room number is mandatory');
        $('#room-number').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('ROOM_ROOM_NUMBER_DUPLICATE') > -1) {
        $('#room-number').parent().find('.help-block').css('display', 'block');
        $('#room-number').parent().find('.help-block').html('Room number already exist');
        $('#room-number').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('ROOM_ROOM_NUMBER_TOO_LONG') > -1) {
        $('#room-number').parent().find('.help-block').css('display', 'block');
        $('#room-number').parent().find('.help-block').html('Room number should not exceed 150 characters');
        $('#room-number').parent().addClass('has-error');
    }
}