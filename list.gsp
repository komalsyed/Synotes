<html>
<head>
	<title>Multimedia List</title>
	<meta name="layout" content="main"/>
</head>
<body>
	<div class="nav">
		<g:isLoggedIn>
			<span class="menuButton"><g:link class="create" action="create" title="Create recording">Create Recording</g:link></span>
		</g:isLoggedIn>
	</div>
	<div class="body">
		<h1>Recording List</h1>
		<g:render template="/common/message"/>
		<div class="list">
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="id" title="Id"/>
						<g:sortableColumn property="title" title="Title"/>
						<g:sortableColumn property="owner" title="Owner"/>
						<g:sortableColumn property="perm" title="Public Permission"/>
						<g:isLoggedIn>
							<th class="sortable">Your Permission</th>
						</g:isLoggedIn>
						<th/>
						<th/>
					</tr>
				</thead>
				<tbody>
					<g:each status="i" var="multimediaResource" in="${multimediaResourceList}">
						<tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td>${fieldValue(bean: multimediaResource, field: 'id')}</td>
							<td><g:link action="show" id="${multimediaResource.id}" title="${fieldValue(bean: multimediaResource, field: 'title')}">${fieldValue(bean: multimediaResource, field: 'title')}</g:link></td>
							<td>${fieldValue(bean: multimediaResource, field: 'owner')}</td>
							<td>
								<g:if test="${fieldValue(bean: multimediaResource, field: 'perm')}">
									${fieldValue(bean: multimediaResource, field: 'perm')}
								</g:if>
								<g:else>
									PRIVATE
								</g:else>
							</td>
							<g:isLoggedIn>
								<td>${permList.get(i).encodeAsHTML()}</td>
							</g:isLoggedIn>				
							<td><g:link controller="recording" action="replay" id="${multimediaResource.id}" title="Replay">Replay</g:link></td>
							<td><g:link controller="recording" action="test2" id="${multimediaResource.id}" title="Test yourself!">Take Test</g:link></td>
							<td><g:link controller="recording" action="uploadtest" id="${multimediaResource.id}" title="Create a Test for students">Make Test</g:link></td>
							<td><g:link controller="recording" action="edittest" id="${multimediaResource.id}" title="Edit Existing Test">Edit Test</g:link></td>
							<td><g:link controller="recording" action="print" id="${multimediaResource.id}" title="Display text and images for printing">Print</g:link></td>
						</tr>
					</g:each>
				</tbody>
			</table>
		</div>
		<div class="paginateButtons">
			<g:paginate total="${multimediaResourcesCount}"/>
		</div>
	</div>
</body>
</html>
