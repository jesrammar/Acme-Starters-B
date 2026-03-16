
package acme.features.any.auditor.auditSection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.auditReport.id = :reportId and s.auditReport.draftMode = false")
	Collection<AuditSection> findPublishedSectionsByReportId(int reportId);

	@Query("select s from AuditSection s where s.id = :id and s.auditReport.draftMode = false")
	AuditSection findPublishedAuditSectionById(int id);

	@Query("select count(r) from AuditReport r where r.id = :reportId and r.draftMode = false")
	int countPublishedReportsById(int reportId);
}
