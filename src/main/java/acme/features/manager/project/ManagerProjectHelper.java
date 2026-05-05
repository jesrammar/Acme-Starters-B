package acme.features.manager.project;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import acme.client.components.principals.UserAccount;
import acme.entities.projects.Project;

public final class ManagerProjectHelper {

	private ManagerProjectHelper() {
	}

	public static String formatMemberUsernames(final Project project) {
		return project.getMembers().stream().map(UserAccount::getUsername).sorted().collect(Collectors.joining(", "));
	}

	public static Set<String> parseUsernames(final String raw) {
		if (raw == null || raw.isBlank())
			return new LinkedHashSet<>();

		return Arrays.stream(raw.split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toCollection(LinkedHashSet::new));
	}

}
