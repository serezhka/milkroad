/**
 * Created by Sergey on 01.03.2016.
 */

/**
 * Updates order info with orderlist.jsp table row data
 *
 * @param orderID
 */
function updateOrderDetails(orderID) {
    $.ajax({
            method: "POST",
            url: "/management/updateOrder",
            dataType: "json",
            data: {
                orderID: orderID,
                paymentMethod: $("select#paymentMethod_" + orderID).val(),
                paymentStatus: $("select#paymentStatus_" + orderID).val(),
                shippingMethod: $("select#shippingMethod_" + orderID).val(),
                shippingStatus: $("select#shippingStatus_" + orderID).val(),
                returnType: "json"
            }
        })
        .done(function (msg) {
            if (msg.message) {
                alert(msg.message);
            } else {
                $("tr#order_" + orderID).effect('highlight', {color: 'green'}, 1000);
            }
        });

    /*var form = document.createElement("form");
     form.method = "POST";
     form.action = "/management/updateOrder";

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
     form.submit();*/
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

function updateAddress(addressID) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/profile/editAddress";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "updateAddress";
    action.type = 'hidden';
    form.appendChild(action);

    var address_id = document.createElement("INPUT");
    address_id.name = "addressID";
    address_id.value = addressID;
    address_id.type = 'hidden';
    form.appendChild(address_id);

    var country = document.createElement("INPUT");
    country.name = "country";
    country.value = $("input#country_" + addressID).val();
    country.type = 'hidden';
    form.appendChild(country);

    var city = document.createElement("INPUT");
    city.name = "city";
    city.value = $("input#city_" + addressID).val();
    city.type = 'hidden';
    form.appendChild(city);

    var postcode = document.createElement("INPUT");
    postcode.name = "postcode";
    postcode.value = $("input#postcode_" + addressID).val();
    postcode.type = 'hidden';
    form.appendChild(postcode);

    var street = document.createElement("INPUT");
    street.name = "street";
    street.value = $("input#street_" + addressID).val();
    street.type = 'hidden';
    form.appendChild(street);

    var building = document.createElement("INPUT");
    building.name = "building";
    building.value = $("input#building_" + addressID).val();
    building.type = 'hidden';
    form.appendChild(building);

    var apartment = document.createElement("INPUT");
    apartment.name = "apartment";
    apartment.value = $("input#apartment_" + addressID).val();
    apartment.type = 'hidden';
    form.appendChild(apartment);

    document.body.appendChild(form);
    form.submit();
}

function createAddress() {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/profile/addAddress";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "createAddress";
    action.type = 'hidden';
    form.appendChild(action);

    var country = document.createElement("INPUT");
    country.name = "country";
    country.value = $("input#country_new").val();
    country.type = 'hidden';
    form.appendChild(country);

    var city = document.createElement("INPUT");
    city.name = "city";
    city.value = $("input#city_new").val();
    city.type = 'hidden';
    form.appendChild(city);

    var postcode = document.createElement("INPUT");
    postcode.name = "postcode";
    postcode.value = $("input#postcode_new").val();
    postcode.type = 'hidden';
    form.appendChild(postcode);

    var street = document.createElement("INPUT");
    street.name = "street";
    street.value = $("input#street_new").val();
    street.type = 'hidden';
    form.appendChild(street);

    var building = document.createElement("INPUT");
    building.name = "building";
    building.value = $("input#building_new").val();
    building.type = 'hidden';
    form.appendChild(building);

    var apartment = document.createElement("INPUT");
    apartment.name = "apartment";
    apartment.value = $("input#apartment_new").val();
    apartment.type = 'hidden';
    form.appendChild(apartment);

    document.body.appendChild(form);
    form.submit();
}

function addProductToCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/addProduct";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "addProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function removeProductFromCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/removeProduct";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "removeProduct";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function removeProductOnceFromCart(article_id) {
    var form = document.createElement("form");
    form.method = "POST";
    form.action = "/cart/removeProductOnce";

    var action = document.createElement("INPUT");
    action.name = "action";
    action.value = "removeProductOnce";
    action.type = 'hidden';
    form.appendChild(action);

    var article = document.createElement("INPUT");
    article.name = "article";
    article.value = article_id;
    article.type = 'hidden';
    form.appendChild(article);

    document.body.appendChild(form);
    form.submit();
}

function showNewProductImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(".product-add-image-div img").attr("src", e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function addProductDetailField() {
    var allSelects = $('.product-add-details-div .selectpicker');
    var lastSelect = $(allSelects).last();
    if ($(lastSelect).find("option:selected").val()) {
        var newSelectDiv = $(lastSelect).parent().parent().clone();
        $(newSelectDiv).find(".bootstrap-select").remove();
        $(newSelectDiv).find("a").click(function () {
            removeProductDetailField($(newSelectDiv));
        });
        var newSelect = $(lastSelect).clone();
        $(newSelect).attr("id", "parameter_" + $(allSelects).size());
        $(newSelect).val("");
        $(newSelect).change(function () {
            if ($(this).val()) {
                addProductDetailField();
            }
        });
        $(newSelect).prependTo($(newSelectDiv));
        $(newSelectDiv).appendTo($(lastSelect).parent().parent().parent());
        $(newSelect).selectpicker();
    }
}

function removeProductDetailField(field) {
    if ($(field).find(".selectpicker").attr("id") == "parameter_0") {
        $(field).find(".selectpicker option").first().attr("selected", true);
        $(field).find(".selectpicker").selectpicker("refresh");
    } else {
        $(field).remove();
    }
}

function addOrEditProduct(form) {
    var formData = new FormData($(form)[0]);
    $.ajax({
        url: $(form).attr("action"),
        type: $(form).attr("method"),
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (returndata) {
            alert(returndata);
        }
    });
    return false;
}

$(document).ready(function () {
    $(".product-add-image-div input").change(function () {
        showNewProductImage(this);
    });

    $('.product-add-details-div .selectpicker').each(function () {
        $(this).change(function () {
            if ($(this).val()) {
                addProductDetailField();
            }
        });
    });

    $('.product-add-details-div a').each(function () {
        $(this).click(function () {
            removeProductDetailField($(this).parent());
        });
    });

    $("#product-data-form").submit(function (e) {
        addOrEditProduct($(this));
        e.preventDefault();
    });
});
