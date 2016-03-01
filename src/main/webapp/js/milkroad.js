/**
 * Created by Sergey on 01.03.2016.
 */

/**
 * Updates order info with orderlist.jsp table row data
 *
 * @param orderID
 */
function updateOrderDetails(orderID) {
    /*$.post(
     "/management",
     {
     action: "updateOrder",
     orderID: orderID,
     paymentMethod: $("select#paymentMethod_" + orderID).val(),
     paymentStatus: $("select#paymentStatus_" + orderID).val(),
     shippingMethod: $("select#shippingMethod_" + orderID).val(),
     shippingStatus: $("select#shippingStatus_" + orderID).val()
     },
     window.location.reload()
     );*/

    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "updateOrder";
    action.type = 'hidden';
    form.appendChild(action);

    var order_id = document.createElement("INPUT");
    order_id.name = "orderID";
    order_id.value = orderID;
    order_id.type = 'hidden';
    form.appendChild(order_id);

    var paymentMethod = document.createElement("INPUT");
    paymentMethod.name = "paymentMethod";
    paymentMethod.value = $("select#paymentMethod_" + orderID).val();
    paymentMethod.type = 'hidden';
    form.appendChild(paymentMethod);

    var paymentStatus = document.createElement("INPUT");
    paymentStatus.name = "paymentStatus";
    paymentStatus.value = $("select#paymentStatus_" + orderID).val();
    paymentStatus.type = 'hidden';
    form.appendChild(paymentStatus);

    var shippingMethod = document.createElement("INPUT");
    shippingMethod.name = "shippingMethod";
    shippingMethod.value = $("select#shippingMethod_" + orderID).val();
    shippingMethod.type = 'hidden';
    form.appendChild(shippingMethod);

    var shippingStatus = document.createElement("INPUT");
    shippingStatus.name = "shippingStatus";
    shippingStatus.value = $("select#shippingStatus_" + orderID).val();
    shippingStatus.type = 'hidden';
    form.appendChild(shippingStatus);

    document.body.appendChild(form);
    form.submit();
}