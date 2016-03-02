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

function updateProduct(productArticle, paramsCount) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "updateProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var product_article = document.createElement("INPUT");
    product_article.name = "productArticle";
    product_article.value = productArticle;
    product_article.type = 'hidden';
    form.appendChild(product_article);

    var productName = document.createElement("INPUT");
    productName.name = "productName";
    productName.value = $("input#productName_" + productArticle).val();
    productName.type = 'hidden';
    form.appendChild(productName);

    var productCategory = document.createElement("INPUT");
    productCategory.name = "productCategoryID";
    productCategory.value = $("select#productCategory_" + productArticle).val();
    productCategory.type = 'hidden';
    form.appendChild(productCategory);

    var productPrice = document.createElement("INPUT");
    productPrice.name = "productPrice";
    productPrice.value = $("input#productPrice_" + productArticle).val();
    productPrice.type = 'hidden';
    form.appendChild(productPrice);

    var productCount = document.createElement("INPUT");
    productCount.name = "productCount";
    productCount.value = $("input#productCount_" + productArticle).val();
    productCount.type = 'hidden';
    form.appendChild(productCount);

    var productDescription = document.createElement("INPUT");
    productDescription.name = "productDescription";
    productDescription.value = $("input#productDesc_" + productArticle).val();
    productDescription.type = 'hidden';
    form.appendChild(productDescription);

    for (var i = 0; i < paramsCount; i++) {
        var productParameter = document.createElement("INPUT");
        var paramID = $("select#productAttrID_" + productArticle + "_" + i).val();
        var paramValue = $("input#productAttrValue_" + productArticle + "_" + i).val();
        productParameter.name = "productParameter"; // for PHP: [" + paramName + "]";
        productParameter.value = paramID + "|" + paramValue;
        productParameter.type = 'hidden';
        form.appendChild(productParameter);
    }

    var paramNewID = $("select#productAttrNewID_" + productArticle).val();
    var paramNewValue = $("input#productAttrNewValue_" + productArticle).val();
    if (paramNewID && paramNewValue && !(paramNewID.length === 0) && !(paramNewValue === 0)) {
        var productNewParameter = document.createElement("INPUT");
        productNewParameter.name = "productParameter"; // for PHP : [" + paramNewName + "]";
        productNewParameter.value = paramNewID + "|" + paramNewValue;
        productNewParameter.type = 'hidden';
        form.appendChild(productNewParameter);
    }

    document.body.appendChild(form);
    form.submit();
}

function createProduct() {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/management";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "createProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var productName = document.createElement("INPUT");
    productName.name = "productName";
    productName.value = $("input#productNewName").val();
    productName.type = 'hidden';
    form.appendChild(productName);

    var productCategory = document.createElement("INPUT");
    productCategory.name = "productCategoryID";
    productCategory.value = $("select#productNewCategory").val();
    productCategory.type = 'hidden';
    form.appendChild(productCategory);

    var productPrice = document.createElement("INPUT");
    productPrice.name = "productPrice";
    productPrice.value = $("input#productNewPrice").val();
    productPrice.type = 'hidden';
    form.appendChild(productPrice);

    var productCount = document.createElement("INPUT");
    productCount.name = "productCount";
    productCount.value = $("input#productNewCount").val();
    productCount.type = 'hidden';
    form.appendChild(productCount);

    var productDescription = document.createElement("INPUT");
    productDescription.name = "productDescription";
    productDescription.value = $("input#productNewDesc").val();
    productDescription.type = 'hidden';
    form.appendChild(productDescription);

    var paramNewID = $("select#productNewAttrID").val();
    var paramNewValue = $("input#productNewAttrValue").val();
    if (paramNewID && paramNewValue && !(paramNewID.length === 0) && !(paramNewValue === 0)) {
        var productNewParameter = document.createElement("INPUT");
        productNewParameter.name = "productParameter"; // for PHP : [" + paramNewName + "]";
        productNewParameter.value = paramNewID + "|" + paramNewValue;
        productNewParameter.type = 'hidden';
        form.appendChild(productNewParameter);
    }

    document.body.appendChild(form);
    form.submit();
}