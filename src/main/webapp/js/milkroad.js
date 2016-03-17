/**
 * Created by Sergey on 01.03.2016.
 */
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

    $("span.error").hide();
});

/**
 * Updates order info with orderlist.jsp table row data
 *
 * @param id order id
 */
function updateOrderDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateOrder",
            dataType: "json",
            data: {
                id: id,
                paymentMethod: $("select#paymentMethod_" + id).val(),
                paymentStatus: $("select#paymentStatus_" + id).val(),
                shippingMethod: $("select#shippingMethod_" + id).val(),
                shippingStatus: $("select#shippingStatus_" + id).val()
            }
        })
        .done(function (data) {
            if (data.errors && data.errors.length > 0) {
                $.each(data.errors, function (idx, val) {
                    alert(val);
                });
                $("tr#order_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#order_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Updates category info
 *
 * @param id category id
 */
function updateCategoryDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateCategory",
            dataType: "json",
            data: {
                id: id,
                name: $("input#categoryName_" + id).val(),
                description: $("input#categoryDesc_" + id).val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && data.errors.length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#" + val).show();
                });
                $("tr#category_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#category_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Updates attribute info
 *
 * @param id attribute id
 */
function updateAttributeDetails(id) {
    $.ajax({
            method: "POST",
            url: "/management/updateAttribute",
            dataType: "json",
            data: {
                id: id,
                name: $("input#attributeName_" + id).val(),
                description: $("input#attributeDesc_" + id).val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && data.errors.length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#" + val).show();
                });
                $("tr#attribute_" + id).effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#attribute_" + id).effect('highlight', {color: 'green'}, 1000);
            }
        });
}

/**
 * Creates new category
 */
function addCategory() {
    $.ajax({
            method: "POST",
            url: "/management/createCategory",
            dataType: "json",
            data: {
                name: $("input#categoryNameNew").val(),
                description: $("input#categoryDescNew").val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && data.errors.length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#" + val).show();
                });
                $("tr#category_new").effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#category_new").effect('highlight', {color: 'green'}, 1000);
                window.location.reload();
            }
        });
}

/**
 * Creates new attribute
 */
function addAttribute() {
    $.ajax({
            method: "POST",
            url: "/management/createAttribute",
            dataType: "json",
            data: {
                name: $("input#attributeNameNew").val(),
                description: $("input#attributeDescNew").val()
            }
        })
        .done(function (data) {
            $("span.error").hide();
            if (data.errors && data.errors.length > 0) {
                $.each(data.errors, function (idx, val) {
                    $("span.error#" + val).show();
                });
                $("tr#attribute_new").effect('highlight', {color: 'red'}, 1000);
            } else {
                $("tr#attribute_new").effect('highlight', {color: 'green'}, 1000);
                window.location.reload();
            }
        });
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
