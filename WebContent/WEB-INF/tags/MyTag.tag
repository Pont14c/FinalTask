<%@ include file="/jspf/directive/taglib.jspf"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="ua.nure.ivanovv.SummaryTask4.resources.resources" />

<fmt:message key="messeges_jsp.already_reserve" />