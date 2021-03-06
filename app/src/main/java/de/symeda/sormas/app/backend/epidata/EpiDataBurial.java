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

package de.symeda.sormas.app.backend.epidata;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.symeda.sormas.api.utils.DateHelper;
import de.symeda.sormas.api.utils.YesNoUnknown;
import de.symeda.sormas.app.backend.common.AbstractDomainObject;
import de.symeda.sormas.app.backend.common.EmbeddedAdo;
import de.symeda.sormas.app.backend.location.Location;

/**
 * Created by Mate Strysewske on 08.03.2017.
 */

@Entity(name = EpiDataBurial.TABLE_NAME)
@DatabaseTable(tableName = EpiDataBurial.TABLE_NAME)
@EmbeddedAdo(parentAccessor = EpiDataBurial.EPI_DATA)
public class EpiDataBurial extends AbstractDomainObject {

    private static final long serialVersionUID = 866789458483672591L;

    public static final String TABLE_NAME = "epidataburial";
    public static final String I18N_PREFIX = "EpiDataBurial";

    public static final String EPI_DATA = "epiData";
    public static final String BURIAL_ADDRESS = "burialAddress";


    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private EpiData epiData;

    @Column(length=512)
    private String burialPersonname;

    @Column(length=512)
    private String burialRelation;

    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date burialDateFrom;

    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date burialDateTo;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 2)
    private Location burialAddress;

    @Enumerated(EnumType.STRING)
    private YesNoUnknown burialIll;

    @Enumerated(EnumType.STRING)
    private YesNoUnknown burialTouching;

    public EpiData getEpiData() {
        return epiData;
    }

    public void setEpiData(EpiData epiData) {
        this.epiData = epiData;
    }

    public String getBurialPersonname() {
        return burialPersonname;
    }

    public void setBurialPersonname(String burialPersonname) {
        this.burialPersonname = burialPersonname;
    }

    public String getBurialRelation() {
        return burialRelation;
    }

    public void setBurialRelation(String burialRelation) {
        this.burialRelation = burialRelation;
    }

    public Date getBurialDateFrom() {
        return burialDateFrom;
    }

    public void setBurialDateFrom(Date burialDateFrom) {
        this.burialDateFrom = burialDateFrom;
    }

    public Date getBurialDateTo() {
        return burialDateTo;
    }

    public void setBurialDateTo(Date burialDateTo) {
        this.burialDateTo = burialDateTo;
    }

    public Location getBurialAddress() {
        return burialAddress;
    }

    public void setBurialAddress(Location burialAddress) {
        this.burialAddress = burialAddress;
    }

    public YesNoUnknown getBurialIll() {
        return burialIll;
    }

    public void setBurialIll(YesNoUnknown burialIll) {
        this.burialIll = burialIll;
    }

    public YesNoUnknown getBurialTouching() {
        return burialTouching;
    }

    public void setBurialTouching(YesNoUnknown burialTouching) {
        this.burialTouching = burialTouching;
    }

    @Override
    public String getI18nPrefix() {
        return I18N_PREFIX;
    }

    @Override
    public String toString() {
        return super.toString() + " " + DateHelper.formatLocalShortDate(getBurialDateTo());
    }

}
