
package acme.features.auditreport;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select ar from AuditReport ar where ar.auditor.userAccount.id = :userAccountId")
	Iterable<AuditReport> findAuditReportsByAuditorId(int userAccountId);

	// Contar secciones de un report
	@Query("select count(s) from AuditSection s where s.auditReport.id = :reportId")
	long countSectionsByReportId(int reportId);

	// Sumar horas de todas las secciones
	@Query("select sum(s.hours) from AuditSection s where s.auditReport.id = :reportId")
	Integer sumHoursByReportId(int reportId);
}
