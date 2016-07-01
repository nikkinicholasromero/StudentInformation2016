$(document).on('ready', function () {
    initializeDataTables();
    bindUIElementEvents();
});

function initializeDataTables() {
    $("#professor-table").DataTable({
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": "professors/getProfessorForDataTable",
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
            "data": "professorNumber"
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
    $('#modal-form').on('hidden.bs.modal', function (e) {
        clearFormValues();
        clearFormWarnings();
    });

    $('#modal-form .save-button').on('click', function() {
        clearFormWarnings();

        var professor = {
            'professorNumber': $('#professor-number').val(),
            'firstName': $('#first-name').val(),
            'middleName': $('#middle-name').val(),
            'lastName': $('#last-name').val(),
            'gender': $('#gender').val(),
            'birthDate': $('#birth-date').val(),
            'streetAddress': $('#street').val(),
            'cityAddress': $('#city').val(),
            'provinceAddress': $('#province').val()
        };

        var validationStatus = validateProfessor(professor);
        if(validationStatus.status == "SUCCESS") {
            $.ajax({
                'url': '/professors/createProfessor',
                'type': 'POST',
                'contentType': 'application/json;charset=utf-8',
                'data': JSON.stringify(professor),
                'success': function(validationStatus) {
                    if(validationStatus.status == 'SUCCESS') {
                        $('#modal-form').modal('hide');
                        showNotificationModal('Successfully added new professor');
                        $('#professor-table').DataTable().draw();
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
    $('#professor-number').val("");
    $('#first-name').val("");
    $('#middle-name').val("");
    $('#last-name').val("");
    $('#gender').val("Male");
    $('#birth-date').val("");
    $('#street').val("");
    $('#city').val("");
    $('#province').val("");
}

function validateProfessor(professor) {
    var validationStatus = {"status": "SUCCESS", "errorCodeList": []};

    if(!professor.professorNumber) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_PROFESSOR_NUMBER_MISSING");
    } else if(professor.professorNumber.length > 15) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_PROFESSOR_NUMBER_TOO_LONG");
    }

    if(!professor.firstName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_FIRST_NAME_MISSING");
    } else if(professor.firstName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_FIRST_NAME_TOO_LONG");
    }

    if(!professor.middleName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_MIDDLE_NAME_MISSING");
    } else if(professor.middleName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_MIDDLE_NAME_TOO_LONG");
    }

    if(!professor.lastName) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_LAST_NAME_MISSING");
    } else if(professor.lastName.length > 50) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_LAST_NAME_TOO_LONG");
    }

    if(!professor.gender) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_GENDER_MISSING");
    } else if(professor.gender != "Male" && professor.gender != "Female") {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_GENDER_INVALID");
    }

    var birthDateMatcher = /^[0-9]{4}-[0-9]{2}-[0-9]{2}$/;
    if(!professor.birthDate) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_BIRTH_DATE_MISSING");
    } else if (!professor.birthDate.match(birthDateMatcher)){
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_BIRTH_DATE_INVALID");
    }

    if(!professor.streetAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_STREET_ADDRESS_DATE_MISSING");
    } else if(professor.streetAddress.length > 100) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_STREET_ADDRESS_DATE_TOO_LONG");
    }

    if(!professor.cityAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_CITY_ADDRESS_DATE_MISSING");
    } else if(professor.cityAddress.length > 150) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_CITY_ADDRESS_DATE_TOO_LONG");
    }

    if(!professor.provinceAddress) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_PROVINCE_ADDRESS_DATE_MISSING");
    } else if(professor.provinceAddress.length > 100) {
        validationStatus.status = "FAILED";
        validationStatus.errorCodeList.push("PROFESSOR_PROVINCE_ADDRESS_DATE_TOO_LONG");
    }

    return validationStatus;
}

function showValidationStatus(validationStatus) {
    if(validationStatus.errorCodeList.indexOf('PROFESSOR_PROFESSOR_NUMBER_MISSING') > -1) {
        $('#professor-number').parent().find('.help-block').css('display', 'block');
        $('#professor-number').parent().find('.help-block').html('Professor number is mandatory');
        $('#professor-number').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_PROFESSOR_NUMBER_DUPLICATE') > -1) {
        $('#professor-number').parent().find('.help-block').css('display', 'block');
        $('#professor-number').parent().find('.help-block').html('Professor number already exist');
        $('#professor-number').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_PROFESSOR_NUMBER_TOO_LONG') > -1) {
        $('#professor-number').parent().find('.help-block').css('display', 'block');
        $('#professor-number').parent().find('.help-block').html('Professor should not exceed 15 characters');
        $('#professor-number').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_FIRST_NAME_MISSING') > -1) {
        $('#first-name').parent().find('.help-block').css('display', 'block');
        $('#first-name').parent().find('.help-block').html('First name is mandatory');
        $('#first-name').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_FIRST_NAME_TOO_LONG') > -1) {
        $('#first-name').parent().find('.help-block').css('display', 'block');
        $('#first-name').parent().find('.help-block').html('First name should not exceed 50 characters');
        $('#first-name').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_MIDDLE_NAME_MISSING') > -1) {
        $('#middle-name').parent().find('.help-block').css('display', 'block');
        $('#middle-name').parent().find('.help-block').html('Middle name is mandatory');
        $('#middle-name').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_MIDDLE_NAME_TOO_LONG') > -1) {
        $('#middle-name').parent().find('.help-block').css('display', 'block');
        $('#middle-name').parent().find('.help-block').html('Middle name should not exceed 50 characters');
        $('#middle-name').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_LAST_NAME_MISSING') > -1) {
        $('#last-name').parent().find('.help-block').css('display', 'block');
        $('#last-name').parent().find('.help-block').html('Last name is mandatory');
        $('#last-name').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_LAST_NAME_TOO_LONG') > -1) {
        $('#last-name').parent().find('.help-block').css('display', 'block');
        $('#last-name').parent().find('.help-block').html('Last name should not exceed 50 characters');
        $('#last-name').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_GENDER_MISSING') > -1) {
        $('#gender').parent().find('.help-block').css('display', 'block');
        $('#gender').parent().find('.help-block').html('Gender mandatory');
        $('#gender').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_GENDER_INVALID') > -1) {
        $('#gender').parent().find('.help-block').css('display', 'block');
        $('#gender').parent().find('.help-block').html('Gender has invalid value');
        $('#gender').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_BIRTH_DATE_MISSING') > -1) {
        $('#birth-date').parent().parent().find('.help-block').css('display', 'block');
        $('#birth-date').parent().parent().find('.help-block').html('Birth date mandatory');
        $('#birth-date').parent().parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_BIRTH_DATE_INVALID') > -1) {
        $('#birth-date').parent().parent().find('.help-block').css('display', 'block');
        $('#birth-date').parent().parent().find('.help-block').html('Birth date has invalid value');
        $('#birth-date').parent().parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_STREET_ADDRESS_DATE_MISSING') > -1) {
        $('#street').parent().find('.help-block').css('display', 'block');
        $('#street').parent().find('.help-block').html('Street is mandatory');
        $('#street').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_STREET_ADDRESS_DATE_TOO_LONG') > -1) {
        $('#street').parent().find('.help-block').css('display', 'block');
        $('#street').parent().find('.help-block').html('Street should not exceed 150 characters');
        $('#street').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_CITY_ADDRESS_DATE_MISSING') > -1) {
        $('#city').parent().find('.help-block').css('display', 'block');
        $('#city').parent().find('.help-block').html('City is mandatory');
        $('#city').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_CITY_ADDRESS_DATE_TOO_LONG') > -1) {
        $('#city').parent().find('.help-block').css('display', 'block');
        $('#city').parent().find('.help-block').html('City should not exceed 100 characters');
        $('#city').parent().addClass('has-error');
    }

    if(validationStatus.errorCodeList.indexOf('PROFESSOR_PROVINCE_ADDRESS_DATE_MISSING') > -1) {
        $('#province').parent().find('.help-block').css('display', 'block');
        $('#province').parent().find('.help-block').html('Province is mandatory');
        $('#province').parent().addClass('has-error');
    } else if(validationStatus.errorCodeList.indexOf('PROFESSOR_PROVINCE_ADDRESS_DATE_TOO_LONG') > -1) {
        $('#province').parent().find('.help-block').css('display', 'block');
        $('#province').parent().find('.help-block').html('Province should not exceed 100 characters');
        $('#province').parent().addClass('has-error');
    }
}