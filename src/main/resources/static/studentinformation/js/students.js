$(document).on('ready', function () {
    initializeDataTables();
    bindUIElementEvents();
});

function initializeDataTables() {
    $("#student-table").DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "students/getStudentForDataTable",
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
            "data": "studentNumber"
        },{
            "data": "firstName"
        },{
            "data": "middleName"
        },{
            "data": "lastName"
        },{
            "data": "gender"
        },{
            "data": "birthDate"
        },{
            "data": "streetAddress"
        },{
            "data": "cityAddress"
        },{
            "data": "provinceAddress"
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
            'url': '/students/createStudent',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({
                'studentNumber': $('#student-number').val(),
                'firstName': $('#first-name').val(),
                'middleName': $('#middle-name').val(),
                'lastName': $('#last-name').val(),
                'gender': $('#gender').val(),
                'birthDate': $('#birth-date').val(),
                'streetAddress': $('#street').val(),
                'cityAddress': $('#city').val(),
                'provinceAddress': $('#province').val()
            }),
            'success': function(validationStatus) {
                $('#add-student-modal .form-group').each(function() {
                    $(this).find('.help-block').css('display', 'none');
                    $(this).removeClass('has-error');
                });

                if(validationStatus.status == 'FAILED') {
                    if(validationStatus.errorCodeList.indexOf('STUDENT_STUDENT_NUMBER_MISSING') > -1) {
                        $('#student-number').parent().find('.help-block').css('display', 'block');
                        $('#student-number').parent().find('.help-block').html('Student number is mandatory');
                        $('#student-number').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_STUDENT_NUMBER_DUPLICATE') > -1) {
                        $('#student-number').parent().find('.help-block').css('display', 'block');
                        $('#student-number').parent().find('.help-block').html('Student number already exist');
                        $('#student-number').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_STUDENT_NUMBER_TOO_LONG') > -1) {
                        $('#student-number').parent().find('.help-block').css('display', 'block');
                        $('#student-number').parent().find('.help-block').html('Student should not exceed 15 characters');
                        $('#student-number').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_FIRST_NAME_MISSING') > -1) {
                        $('#first-name').parent().find('.help-block').css('display', 'block');
                        $('#first-name').parent().find('.help-block').html('First name is mandatory');
                        $('#first-name').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_FIRST_NAME_TOO_LONG') > -1) {
                        $('#first-name').parent().find('.help-block').css('display', 'block');
                        $('#first-name').parent().find('.help-block').html('First name should not exceed 50 characters');
                        $('#first-name').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_MIDDLE_NAME_MISSING') > -1) {
                        $('#middle-name').parent().find('.help-block').css('display', 'block');
                        $('#middle-name').parent().find('.help-block').html('Middle name is mandatory');
                        $('#middle-name').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_MIDDLE_NAME_TOO_LONG') > -1) {
                        $('#middle-name').parent().find('.help-block').css('display', 'block');
                        $('#middle-name').parent().find('.help-block').html('Middle name should not exceed 50 characters');
                        $('#middle-name').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_LAST_NAME_MISSING') > -1) {
                        $('#last-name').parent().find('.help-block').css('display', 'block');
                        $('#last-name').parent().find('.help-block').html('Last name is mandatory');
                        $('#last-name').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_LAST_NAME_TOO_LONG') > -1) {
                        $('#last-name').parent().find('.help-block').css('display', 'block');
                        $('#last-name').parent().find('.help-block').html('Last name should not exceed 50 characters');
                        $('#last-name').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_GENDER_MISSING') > -1) {
                        $('#gender').parent().find('.help-block').css('display', 'block');
                        $('#gender').parent().find('.help-block').html('Gender mandatory');
                        $('#gender').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_GENDER_INVALID') > -1) {
                        $('#gender').parent().find('.help-block').css('display', 'block');
                        $('#gender').parent().find('.help-block').html('Gender has invalid value');
                        $('#gender').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_BIRTH_DATE_MISSING') > -1) {
                        $('#birth-date').parent().find('.help-block').css('display', 'block');
                        $('#birth-date').parent().find('.help-block').html('Birth date mandatory');
                        $('#birth-date').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_STREET_ADDRESS_DATE_MISSING') > -1) {
                        $('#street').parent().find('.help-block').css('display', 'block');
                        $('#street').parent().find('.help-block').html('Street is mandatory');
                        $('#street').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_STREET_ADDRESS_DATE_TOO_LONG') > -1) {
                        $('#street').parent().find('.help-block').css('display', 'block');
                        $('#street').parent().find('.help-block').html('Street should not exceed 150 characters');
                        $('#street').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_CITY_ADDRESS_DATE_MISSING') > -1) {
                        $('#city').parent().find('.help-block').css('display', 'block');
                        $('#city').parent().find('.help-block').html('City is mandatory');
                        $('#city').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_CITY_ADDRESS_DATE_TOO_LONG') > -1) {
                        $('#city').parent().find('.help-block').css('display', 'block');
                        $('#city').parent().find('.help-block').html('City should not exceed 100 characters');
                        $('#city').parent().addClass('has-error');
                    }

                    if(validationStatus.errorCodeList.indexOf('STUDENT_PROVINCE_ADDRESS_DATE_MISSING') > -1) {
                        $('#province').parent().find('.help-block').css('display', 'block');
                        $('#province').parent().find('.help-block').html('Province is mandatory');
                        $('#province').parent().addClass('has-error');
                    } else if(validationStatus.errorCodeList.indexOf('STUDENT_PROVINCE_ADDRESS_DATE_TOO_LONG') > -1) {
                        $('#province').parent().find('.help-block').css('display', 'block');
                        $('#province').parent().find('.help-block').html('Province should not exceed 100 characters');
                        $('#province').parent().addClass('has-error');
                    }
                } else {
                    $('#add-student-modal').modal('hide');

                    $('.notification-modal p').html('Successfully added new student');
                    $('.notification-modal').addClass('notification-modal-show');
                    setTimeout(function() {
                        $('.notification-modal').removeClass('notification-modal-show');
                    }, 3000);

                    $('#student-table').DataTable().draw();
                }
            }
       });
    });
}