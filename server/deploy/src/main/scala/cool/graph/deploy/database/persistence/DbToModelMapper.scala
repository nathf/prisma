package cool.graph.deploy.database.persistence

import cool.graph.deploy.database.tables.{Migration, Project}
import cool.graph.shared.models
import cool.graph.shared.models.MigrationStep

object DbToModelMapper {
  import cool.graph.shared.models.ProjectJsonFormatter._
  import MigrationStepsJsonFormatter._

  def convert(projectId: String, migration: Migration): models.Project = {
    val projectModel = migration.schema.as[models.Project]
    projectModel.copy(revision = migration.revision)
  }

  def convert(project: Project, migration: Migration): models.Project = {
    val projectModel = migration.schema.as[models.Project]
    projectModel.copy(revision = migration.revision)
  }

  def convert(project: Project): models.Project = {
    // todo fix shared project model
    models.Project(id = project.id, ownerId = project.ownerId.getOrElse(""))
  }

  def convert(migration: Migration): models.Migration = {
    models.Migration(
      migration.projectId,
      migration.revision,
      migration.status,
      migration.progress,
      migration.steps.as[Vector[MigrationStep]],
      migration.errors.as[Vector[String]]
    )
  }
}