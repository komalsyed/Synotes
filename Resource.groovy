class Resource
{
	static searchable = true

	static belongsTo = [owner: User]

	static hasMany = [permissions: ResourcePermission, annotates: Annotation, annotations: ResourceAnnotation]
	static mappedBy = [annotates: "source", annotations: "target"]

	String title
	PermissionValue perm
	String flag

	static constraints = {
		title(nullable: true, blank: false)
		perm(nullable: true)
		flag(nullable: true, blank: false)
	}

	String toString()
	{
		if (title)
			return title

		return (title) ? title : "${getClass().getSimpleName()} ${getId()}"
	}
}
