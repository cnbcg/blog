'use strict';
app.factory('messageService', function ($timeout) {

    var timeout,  messageService =  {
        showMessage: function (message) {

            $("#message").stop(false, true);
            if (timeout) $timeout.cancel(timeout);

            $("#message").animate({right: -300}, 200).text(message).animate({right: 0}, 200);

            timeout = $timeout(function () {
                messageService.removeMessage();
            }, 3000);

        },
        removeMessage: function () {
            $("#message").animate({right: -300}, 200);
        },
    }

    return messageService;

});