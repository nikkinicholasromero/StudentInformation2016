$(document).on('ready', function () {
    $('.close-notification-modal').on('click', function() {
        $('.notification-modal').removeClass('notification-modal-show');
    });
});