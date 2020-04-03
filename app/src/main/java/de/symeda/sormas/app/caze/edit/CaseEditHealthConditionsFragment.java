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

package de.symeda.sormas.app.caze.edit;

import de.symeda.sormas.app.BaseEditFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.backend.caze.Case;
import de.symeda.sormas.app.backend.clinicalcourse.HealthConditions;
import de.symeda.sormas.app.databinding.FragmentCaseEditHealthConditionsLayoutBinding;

public class CaseEditHealthConditionsFragment extends BaseEditFragment<FragmentCaseEditHealthConditionsLayoutBinding, HealthConditions, Case> {

    public static final String TAG = CaseEditHealthConditionsFragment.class.getSimpleName();

    private HealthConditions record;

    // Static methods

    public static CaseEditHealthConditionsFragment newInstance(Case activityRootData) {
        return newInstance(CaseEditHealthConditionsFragment.class, null, activityRootData);
    }

    // Overrides

    @Override
    protected void prepareFragmentData() {
        Case caze = getActivityRootData();
        record = caze.getClinicalCourse().getHealthConditions();
    }

    @Override
    public void onLayoutBinding(FragmentCaseEditHealthConditionsLayoutBinding contentBinding) {
        contentBinding.setData(record);
    }

    @Override
    protected String getSubHeadingTitle() {
        return getResources().getString(R.string.caption_case_health_conditions);
    }

    @Override
    public HealthConditions getPrimaryData() {
        return record;
    }

    @Override
    public int getEditLayout() {
        return R.layout.fragment_case_edit_health_conditions_layout;
    }

}
