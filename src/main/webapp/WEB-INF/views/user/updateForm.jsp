<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ include file="../layouts/header.jsp"%>

<div class="container">
    <form> 
        <input type="hidden" id="id" value="${principal.user.id}" />
        <div class="form-group">
            <label for="username">Username:</label>
            <input value="${principal.user.username}"type="text" class="form-control" id="username" placeholder="Enter username" name="username" readonly>
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>

        <div class="form-group">
            <label for="email">email:</label>
            <input value="${principal.user.email}" type="email" class="form-control" id="email" placeholder="Enter email" name="email">
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        
        <div class="form-group">
            <label for="password">password:</label>
            <input type="password" class="form-control" id="password" placeholder="Enter password" name="password">
            <div class="valid-feedback">Valid.</div>
            <div class="invalid-feedback">Please fill out this field.</div>
        </div>
        
    </form>
    <button id="btn-update" class="btn btn-primary">회원수정</button>
</div>


<%@ include file="../layouts/footer.jsp"%>
<script src="../../../../../../../js/user.js"></script> 

