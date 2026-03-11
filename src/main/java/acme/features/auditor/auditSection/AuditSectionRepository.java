
package acme.features.auditor.auditSection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditSection;

@Repository
public interface AuditSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);

	@Query("select s from AuditSection s where s.auditReport.id = :reportId")
	Iterable<AuditSection> findSectionsByReportId(int reportId);

	@Query("select s from AuditSection s where s.id = :id and s.auditReport.auditor.id = :auditorId")
	AuditSection findOneByIdAndAuditorId(int id, int auditorId);

}
