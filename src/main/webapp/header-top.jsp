<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Top header (search, login, logout, cart) --%>
<div class="header">
    <div class="header-top">
        <div class="container">
            <div class="search">
                <form>
                    <%--suppress HtmlFormInputWithoutLabel --%>
                    <input type="text" value="Search " onfocus="this.value = '';"
                           onblur="if (this.value == '') {this.value = 'Search';}">
                    <input type="submit" value="Go">
                </form>
            </div>
            <div class="header-left">
                <ul>
                    <li><a href="/login">Login</a></li>
                    <li><a href="/register">Register</a></li>
                </ul>
                <div class="cart">
                    <a href="/cart">
                        <div class="total">
                            <span class="header_cart_total">$100.00</span> (<span id="header_cart_quantity"
                                                                                  class="header_cart_quantity"></span>3
                            items)
                        </div>
                        <img src="images/cart.png" alt=""/>
                    </a>
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
</div>
