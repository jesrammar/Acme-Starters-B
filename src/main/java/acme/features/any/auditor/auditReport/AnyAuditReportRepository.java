
package acme.features.any.auditor.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.draftMode = false")
	Collection<AuditReport> findPublishedAuditReports();

	@Query("select ar from AuditReport ar where ar.id = :id and ar.draftMode = false")
	AuditReport findPublishedAuditReportById(int id);

	@Query("select count(s) from AuditSection s where s.auditReport.id = :reportId")
	long countSectionsByReportId(int reportId);

	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :reportId")
	Integer sumHoursByReportId(int reportId);
}
