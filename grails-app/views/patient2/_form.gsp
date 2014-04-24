<%@ page import="test6grails.Patient2" %>



<div class="fieldcontain ${hasErrors(bean: patient2Instance, field: 'forename', 'error')} ">
	<label for="forename">
		<g:message code="patient2.forename.label" default="Forename" />
		
	</label>
	<g:textField name="forename" value="${patient2Instance?.forename}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: patient2Instance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="patient2.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${patient2Instance?.name}"/>
</div>

