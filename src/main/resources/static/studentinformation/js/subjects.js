var activeUuid = '';
var actionUrl = '/subjects';
var notificationMessage = 'Operation is successful';

$(document).on('ready', function () {
    initializeDataTables();
    bindUIElementEvents();
});

function initializeDataTables() {
    $("#subject-table").DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "subjects/getSubjectForDataTable",
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
            "data": "code"
        },{
            "data": "title"
        },{
            "data": "units"
        },{
            "data": "hours"
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
        $('#modal-form .modal-title').html('Add Subject');
        activeUuid = '';
        actionUrl = '/subjects/createSubject';
        notificationMessage = 'Successfully added new subject';
    });

    $(document).on('click', '.edit-button', function() {
        $('#modal-form .modal-title').html('Edit Subject');
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/subjects/updateSubject';
        notificationMessage = 'Successfully updated subject';

        $('#modal-form').modal('show');
        $.ajax({
            'url': '/subjects/getSubject',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({'uuid': activeUuid}),
            'success': function(subject) {
                $('#code').val(subject.code);
                $('#title').val(subject.title);
                $('#units').val(subject.units);
                $('#hours').val(subject.hours);
            }
        });
    });

    $(document).on('click', '.delete-button', function() {
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/subjects/deleteSubject';
        notificationMessage = 'Successfully deleted subject';
    });

    $('#modal-form').on('hidden.bs.modal', function(e) {
        clearFormValues();
        clearFormWarnings();
    });

    $('#modal-form .save-button').on('click', function() {
        clearFormWarnings();

        var subject = {
            'uuid': activeUuid,
            'code': $('#code').val(),
            'title': $('#title').val(),
            'units': $('#units').val(),
            'hours': $('#hours').val()
        };

        var validationStatus = validateSubject(subject);
        if(validationStatus.status == "SUCCESS") {
            $.ajax({
                'url': actionUrl,
                'type': 'POST',
                'contentType': 'application/json;charset=utf-8',
                'data': JSON.stringify(subject),
                'success': function(validationStatus) {
                    if(validationStatus.status == 'SUCCESS') {
                        $('#modal-form').modal('hide');
                        showNotificationModal(notificationMessage);
                        $('#subject-table').DataTable().draw();
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
    $('#code').val("");
    $('#title').val("");
    $('#units').val("0");
    $('#hours').val("0");
}

function validateSubject(subject) {
    var validationStatus = {"status": "SUCCESS", "errorCodeList": []};

    if(!subject.code) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_CODE_MISSING");
    } else if(subject.code.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_CODE_TOO_LONG");
    }

    if(!subject.title) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_TITLE_MISSING");
    } else if(subject.title.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_TITLE_TOO_LONG");
    }

    if(!subject.units) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_UNITS_MISSING");
    } else if(parseFloat(subject.units) == 0) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_UNITS_INVALID");
    }

    if(!subject.hours) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_HOURS_MISSING");
    } else if(parseFloat(subject.hours) == 0) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("SUBJECT_HOURS_INVALID");
    }

    return validationStatus;
}

function showValidationStatus(validationStatus) {
    if(validationStatus.errorCodeList.indexOf('SUBJECT_CODE_MISSING') > -1) {
        $('#code').parent().find('.help-block').css('display', 'block');
        $('#code').parent().find('.help-block').html('Code is mandatory');
        $('#code').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('SUBJECT_CODE_DUPLICATE') > -1) {
        $('#code').parent().find('.help-block').css('display', 'block');
        $('#code').parent().find('.help-block').html('Code already exist');
        $('#code').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('SUBJECT_CODE_TOO_LONG') > -1) {
        $('#code').parent().find('.help-block').css('display', 'block');
        $('#code').parent().find('.help-block').html('Code should not exceed 50 characters');
        $('#code').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('SUBJECT_TITLE_MISSING') > -1) {
        $('#title').parent().find('.help-block').css('display', 'block');
        $('#title').parent().find('.help-block').html('Title is mandatory');
        $('#title').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('SUBJECT_TITLE_TOO_LONG') > -1) {
        $('#title').parent().find('.help-block').css('display', 'block');
        $('#title').parent().find('.help-block').html('Title should not exceed 250 characters');
        $('#title').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('SUBJECT_UNITS_MISSING') > -1) {
        $('#units').parent().find('.help-block').css('display', 'block');
        $('#units').parent().find('.help-block').html('Units is mandatory');
        $('#units').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('SUBJECT_UNITS_INVALID') > -1) {
        $('#units').parent().find('.help-block').css('display', 'block');
        $('#units').parent().find('.help-block').html('Units has invalid value');
        $('#units').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('SUBJECT_HOURS_MISSING') > -1) {
        $('#hours').parent().find('.help-block').css('display', 'block');
        $('#hours').parent().find('.help-block').html('Hours is mandatory');
        $('#hours').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('SUBJECT_HOURS_INVALID') > -1) {
        $('#hours').parent().find('.help-block').css('display', 'block');
        $('#hours').parent().find('.help-block').html('Hours has invalid value');
        $('#hours').parent().addClass('has-error');
    }
}