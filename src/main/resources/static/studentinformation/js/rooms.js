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
                '<button type="button" class="btn btn-block btn-primary btn-sm">Edit</button>' +
                '<button type="button" class="btn btn-block btn-danger btn-sm">Delete</button>'
        }],
        "autoWidth": false
    });
}

function bindUIElementEvents() {
    $('.add-modal .save-button').on('click', function() {
       $.ajax({
            'url': '/rooms/createRoom',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({
                'roomNumber': $('#room-number').val()
            }),
            'success': function(validationStatus) {
                $('#add-room-modal .form-group').each(function() {
                    $(this).find('.help-block').css('display', 'none');
                    $(this).removeClass('has-error');
                });

                if(validationStatus.status == 'FAILED') {
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
                } else {
                    $('#add-room-modal').modal('hide');

                    $('.notification-modal p').html('Successfully added new room');
                    $('.notification-modal').addClass('notification-modal-show');
                    setTimeout(function() {
                        $('.notification-modal').removeClass('notification-modal-show');
                    }, 3000);

                    $('#room-table').DataTable().draw();
                }
            }
       });
    });
}