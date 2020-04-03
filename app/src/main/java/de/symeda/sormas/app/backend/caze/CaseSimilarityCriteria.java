/*
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.symeda.sormas.app.backend.caze;

import java.io.Serializable;
import java.util.Date;

import de.symeda.sormas.api.Disease;
import de.symeda.sormas.api.caze.CaseClassification;
import de.symeda.sormas.api.caze.CaseOrigin;
import de.symeda.sormas.api.caze.CaseOutcome;
import de.symeda.sormas.api.caze.InvestigationStatus;
import de.symeda.sormas.api.utils.EpiWeek;
import de.symeda.sormas.app.backend.region.Region;

public class CaseSimilarityCriteria implements Serializable {

    private CaseCriteria caseCriteria;
    private String firstName;
    private String lastName;
    private Date reportDate;

    public CaseCriteria getCaseCriteria() {
        return caseCriteria;
    }

    public CaseSimilarityCriteria setCaseCriteria(CaseCriteria caseCriteria) {
        this.caseCriteria = caseCriteria;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CaseSimilarityCriteria setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CaseSimilarityCriteria setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public CaseSimilarityCriteria setReportDate(Date reportDate) {
        this.reportDate = reportDate;
        return this;
    }
}
