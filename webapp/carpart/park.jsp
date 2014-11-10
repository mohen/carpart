<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/common/include/taglib.jsp"%>
<G4Studio:html title="停车场管理" >
<G4Studio:import src="/carpart/js/park.js" />
<G4Studio:ext.codeRender fields="QYBZ" />
<G4Studio:ext.codeStore fields="QYBZ" showCode="false"/>
<G4Studio:ext.codeRender fields="CITYCODE" />
<G4Studio:ext.codeStore fields="CITYCODE" showCode="false"/>
<%-- 自定义表格行高 
<style type="text/css">
    .x-grid3-row{
        height:80px;
    }
</style>
--%>
<G4Studio:body>
</G4Studio:body>
</G4Studio:html>