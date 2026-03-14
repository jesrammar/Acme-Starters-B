
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;

@Repository
public interface AuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);

	@Query("select s from AuditSection s where s.auditReport.id = :reportId and s.auditReport.auditor.id = :auditorId")
	Collection<AuditSection> findSectionsByReportIdAndAuditorId(int reportId, int auditorId);

	@Query("select r from AuditReport r where r.id = :reportId and r.auditor.id = :auditorId")
	AuditReport findAuditReportByIdAndAuditorId(int reportId, int auditorId);

	@Query("select s from AuditSection s where s.id = :id and s.auditReport.auditor.id = :auditorId")
	AuditSection findOneByIdAndAuditorId(int id, int auditorId);

}
