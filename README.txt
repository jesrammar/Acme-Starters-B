# README.txt
#
# Copyright (C) 2012-2026 Rafael Corchuelo.
#
# In keeping with the traditional purpose of furthering education and research, it is
# the policy of the copyright owner to permit non-commercial use and redistribution of
# this software. It has been tested carefully, but it is not guaranteed for any particular
# purposes.  The copyright owner does not offer any warranties or representations, nor do
# they accept any liabilities with respect to them.

This is a starter project. It is intended to be a core learning asset for the students
who have enrolled in the Design and Testing subject of the Software Engineering curriculum
of the University of Seville. This project helps them start working on their new information
system projects.

The application is a web-based information system developed using the Acme Framework.
It provides a modular architecture that supports multiple user roles and business features,
implemented incrementally as part of an academic assignment.

Key characteristics
- Layered architecture (presentation, service, persistence)
- Role-based access control
- Database-backed domain model with validation constraints
- Built-in support for testing and incremental development

Typical business features (examples)
- Campaign management (create, update, publish)
- Milestone tracking and effort aggregation
- Public browsing of published information

Quick start (summary)
1) Configure the appropriate Acme-Framework version
2) Create the required databases (dev/test)
3) Run the populator to initialise schema and sample data
4) Launch the web application and verify the welcome page

To get this project up and running, please follow the guidelines in the theory/lab
materials, taking into account that you must link the appropriate version of
the Acme-Framework excluding the following resources:

- **/fragments/**
