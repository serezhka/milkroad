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

function updateCategoryDetails(categoryID) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "updateCategory";
    action.type = 'hidden';
    form.appendChild(action);

    var category_id = document.createElement("INPUT");
    category_id.name = "categoryID";
    category_id.value = categoryID;
    category_id.type = 'hidden';
    form.appendChild(category_id);

    var categoryName = document.createElement("INPUT");
    categoryName.name = "categoryName";
    categoryName.value = $("input#categoryName_" + categoryID).val();
    categoryName.type = 'hidden';
    form.appendChild(categoryName);

    var categoryDesc = document.createElement("INPUT");
    categoryDesc.name = "categoryDescription";
    categoryDesc.value = $("input#categoryDesc_" + categoryID).val();
    categoryDesc.type = 'hidden';
    form.appendChild(categoryDesc);

    document.body.appendChild(form);
    form.submit();
}

function addCategory() {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "createCategory";
    action.type = 'hidden';
    form.appendChild(action);

    var categoryName = document.createElement("INPUT");
    categoryName.name = "categoryName";
    categoryName.value = $("input#categoryNameNew").val();
    categoryName.type = 'hidden';
    form.appendChild(categoryName);

    var categoryDesc = document.createElement("INPUT");
    categoryDesc.name = "categoryDescription";
    categoryDesc.value = $("input#categoryDescNew").val();
    categoryDesc.type = 'hidden';
    form.appendChild(categoryDesc);

    document.body.appendChild(form);
    form.submit();
}

function updateAttributeDetails(attributeID) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "updateAttribute";
    action.type = 'hidden';
    form.appendChild(action);

    var attribute_id = document.createElement("INPUT");
    attribute_id.name = "attributeID";
    attribute_id.value = attributeID;
    attribute_id.type = 'hidden';
    form.appendChild(attribute_id);

    var attributeName = document.createElement("INPUT");
    attributeName.name = "attributeName";
    attributeName.value = $("input#attributeName_" + attributeID).val();
    attributeName.type = 'hidden';
    form.appendChild(attributeName);

    var attributeDesc = document.createElement("INPUT");
    attributeDesc.name = "attributeDescription";
    attributeDesc.value = $("input#attributeDesc_" + attributeID).val();
    attributeDesc.type = 'hidden';
    form.appendChild(attributeDesc);

    document.body.appendChild(form);
    form.submit();
}

function addAttribute() {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "createAttribute";
    action.type = 'hidden';
    form.appendChild(action);

    var attributeName = document.createElement("INPUT");
    attributeName.name = "attributeName";
    attributeName.value = $("input#attributeNameNew").val();
    attributeName.type = 'hidden';
    form.appendChild(attributeName);

    var attributeDesc = document.createElement("INPUT");
    attributeDesc.name = "attributeDescription";
    attributeDesc.value = $("input#attributeDescNew").val();
    attributeDesc.type = 'hidden';
    form.appendChild(attributeDesc);

    document.body.appendChild(form);
    form.submit();
}