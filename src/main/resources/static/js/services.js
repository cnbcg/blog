'use strict';
app.factory('messageService', function ($timeout) {

    var timeout, messageService = {
        showMessage: function (messageType, message) {

            if (messageType !== 'error') {
                $("#message").removeClass("alert-danger");
            }

            if (!$("#message").hasClass("alert-error") && messageType === 'error') {
                $("#message").addClass("alert-danger");
            }

            $("#message").stop(false, true);
            timeout && $timeout.cancel(timeout);

            messageService.removeMessage(function () {
                $("#message").text(message).transition({opacity: 1, right: 0, rotateY: '0deg', scale: '1'}, 300, 'snap')
            });

            timeout = $timeout(function () {
                messageService.removeMessage();
            }, 3000);

        },
        showSuccessMessage: function (message) {
            this.showMessage("success", message);
        },
        showErrorMessage: function (message) {
            this.showMessage("error", message);
        },
        removeMessage: function (callback) {
            $("#message").transition({opacity: 1, right: -300, rotateY: '0deg', scale: '1'}, 300, 'snap', function () {
                callback && callback()
            });
        }
    };

    return messageService;

});