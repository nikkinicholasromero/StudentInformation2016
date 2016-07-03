var activeUuid = '';
var actionUrl = '/students';
var notificationMessage = 'Operation is successful';

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
                '<button type="button" class="btn btn-block btn-primary btn-sm edit-button">Edit</button>' +
                '<button type="button" class="btn btn-block btn-danger btn-sm delete-button" data-toggle="modal" data-target="#delete-modal">Delete</button>'
        }],
        "autoWidth": false
    });
}

function bindUIElementEvents() {
    $(document).on('click', '.add-button', function() {
        $('#modal-form .modal-title').html('Add Student');
        activeUuid = '';
        actionUrl = '/students/createStudent';
        notificationMessage = 'Successfully added new student';
    });

    $(document).on('click', '.edit-button', function() {
        $('#modal-form .modal-title').html('Edit Student');
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/students/updateStudent';
        notificationMessage = 'Successfully updated student';

        $('#modal-form').modal('show');
        $.ajax({
            'url': '/students/getStudent',
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({'uuid': activeUuid}),
            'success': function(student) {
                $('#student-number').val(student.studentNumber);
                $('#first-name').val(student.firstName);
                $('#middle-name').val(student.middleName);
                $('#last-name').val(student.lastName);
                $('#gender').val(student.gender);
                $('#birth-date').val(student.birthDate);
                $('#street').val(student.streetAddress);
                $('#city').val(student.cityAddress);
                $('#province').val(student.provinceAddress);
            }
        });
    });

    $(document).on('click', '.delete-button', function() {
        activeUuid = $(this).parent().parent().find('td.hide-column').html();
        actionUrl = '/students/deleteStudent';
        notificationMessage = 'Successfully deleted student';
    });

    $('#modal-form').on('hidden.bs.modal', function(e) {
        clearFormValues();
        clearFormWarnings();
    });

    $('#modal-form .save-button').on('click', function() {
        clearFormWarnings();

        var student = {
            'uuid': activeUuid,
            'studentNumber': $('#student-number').val(),
            'firstName': $('#first-name').val(),
            'middleName': $('#middle-name').val(),
            'lastName': $('#last-name').val(),
            'gender': $('#gender').val(),
            'birthDate': $('#birth-date').val(),
            'streetAddress': $('#street').val(),
            'cityAddress': $('#city').val(),
            'provinceAddress': $('#province').val()
        };

        var validationStatus = validateStudent(student);
        if(validationStatus.status == "SUCCESS") {
            $.ajax({
                'url': actionUrl,
                'type': 'POST',
                'contentType': 'application/json;charset=utf-8',
                'data': JSON.stringify(student),
                'success': function(validationStatus) {
                    if(validationStatus.status == 'SUCCESS') {
                        $('#modal-form').modal('hide');
                        showNotificationModal(notificationMessage);
                        $('#student-table').DataTable().draw();
                    } else if(validationStatus.status == 'FAILED') {
                        showValidationStatus(validationStatus);
                    }
                }
            });
        } else {
            showValidationStatus(validationStatus);
        }
    });

    $('#delete-modal .save-button').on('click', function() {
        $.ajax({
            'url': actionUrl,
            'type': 'POST',
            'contentType': 'application/json;charset=utf-8',
            'data': JSON.stringify({'uuid': activeUuid}),
            'success': function() {
                $('#delete-modal').modal('hide');
                showNotificationModal(notificationMessage);
                $('#student-table').DataTable().draw();
            }
        });
    });
}

function clearFormValues() {
    $('#student-number').val("");
    $('#first-name').val("");
    $('#middle-name').val("");
    $('#last-name').val("");
    $('#gender').val("Male");
    $('#birth-date').val("");
    $('#street').val("");
    $('#city').val("");
    $('#province').val("");
}

function validateStudent(student) {
    var validationStatus = {"status": "SUCCESS", "errorCodeList": []};

    if(!student.studentNumber) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_STUDENT_NUMBER_MISSING");
    } else if(student.studentNumber.length > 15) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_STUDENT_NUMBER_TOO_LONG");
    }

    if(!student.firstName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_FIRST_NAME_MISSING");
    } else if(student.firstName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_FIRST_NAME_TOO_LONG");
    }

    if(!student.middleName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_MIDDLE_NAME_MISSING");
    } else if(student.middleName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_MIDDLE_NAME_TOO_LONG");
    }

    if(!student.lastName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_LAST_NAME_MISSING");
    } else if(student.lastName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_LAST_NAME_TOO_LONG");
    }

    if(!student.gender) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_GENDER_MISSING");
    } else if(student.gender != "Male" && student.gender != "Female") {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_GENDER_INVALID");
    }

    var birthDateMatcher = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    if(!student.birthDate) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_BIRTH_DATE_MISSING");
    } else if (!student.birthDate.match(birthDateMatcher)){
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_BIRTH_DATE_INVALID");
    }

    if(!student.streetAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_STREET_ADDRESS_DATE_MISSING");
    } else if(student.streetAddress.length > 100) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_STREET_ADDRESS_DATE_TOO_LONG");
    }

    if(!student.cityAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_CITY_ADDRESS_DATE_MISSING");
    } else if(student.cityAddress.length > 150) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_CITY_ADDRESS_DATE_TOO_LONG");
    }

    if(!student.provinceAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_PROVINCE_ADDRESS_DATE_MISSING");
    } else if(student.provinceAddress.length > 100) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("STUDENT_PROVINCE_ADDRESS_DATE_TOO_LONG");
    }

    return validationStatus;
}

function showValidationStatus(validationStatus) {
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
        $('#birth-date').parent().parent().find('.help-block').css('display', 'block');
        $('#birth-date').parent().parent().find('.help-block').html('Birth date mandatory');
        $('#birth-date').parent().parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('STUDENT_BIRTH_DATE_INVALID') > -1) {
        $('#birth-date').parent().parent().find('.help-block').css('display', 'block');
        $('#birth-date').parent().parent().find('.help-block').html('Birth date has invalid value');
        $('#birth-date').parent().parent().addClass('has-error');
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
}