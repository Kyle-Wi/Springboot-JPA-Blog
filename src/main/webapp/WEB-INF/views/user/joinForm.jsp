<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ include file="../layouts/header.jsp"%>

<div class="container">
    <form> 

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" placeholder="Enter username" name="username" required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>

        <div class="form-group">
            <label for="email">email:</label>
            <input type="email" class="form-control" id="email" placeholder="Enter email" name="email" required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        
        <div class="form-group">
            <label for="password">password:</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" name="password" required>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        
        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" name="remember" required> 동의하시겠습니까?
            </label>
        </div>
    </form>
    <button id="btn-save" class="btn btn-primary">회원가입</button>
</div>


<%@ include file="../layouts/footer.jsp"%>
<script src="../../../../../../../blog/js/user.js"></script> 

