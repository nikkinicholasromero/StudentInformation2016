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
            'url': '/subjects/createSubject',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({
                'code': $('#code').val(),
                'title': $('#title').val(),
                'units': $('#units').val()
            }),
            'success': function(validationStatus) {
                $('#add-subject-modal .form-group').each(function() {
                    $(this).find('.help-block').css('display', 'none');
                    $(this).removeClass('has-error');
                });

                if(validationStatus.status == 'FAILED') {
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
                } else {
                    $('#add-subject-modal').modal('hide');

                    $('.notification-modal p').html('Successfully added new subject');
                    $('.notification-modal').addClass('notification-modal-show');
                    setTimeout(function() {
                        $('.notification-modal').removeClass('notification-modal-show');
                    }, 3000);

                    $('#subject-table').DataTable().draw();
                }
            }
       });
    });
}