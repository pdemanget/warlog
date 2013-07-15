/**
 * override standard proxy.
 */
Ext.define('app.util.Ajax', {
    extend: 'Ext.data.proxy.Ajax',
    alias: 'proxy.appajax',
    
    listeners:
    {
        exception: function (proxy, request, operation)
        {
            if (request.responseText != undefined)
            {
                // responseText was returned, decode it
                var responseObj = Ext.decode(request.responseText,true);
                if (responseObj != null && responseObj.message != undefined)
                {
                    // message was returned
                    Ext.Msg.alert('Error',responseObj.message);
                }
                else
                {
                    // responseText was decoded, but no message sent
                    Ext.Msg.alert('Error','Unknown error: The server did not send any information about the error.');
                }
            }
            else
            {
                // no responseText sent
                Ext.Msg.alert('Error','Unknown error: Unable to understand the response from the server');
            }
        }
    }
});