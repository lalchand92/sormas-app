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

package de.symeda.sormas.app.dashboard.sample;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.symeda.sormas.app.BaseSummaryFragment;
import de.symeda.sormas.app.R;
import de.symeda.sormas.app.component.visualization.SummaryCircularProgressBinder;
import de.symeda.sormas.app.component.visualization.SummaryTotalBinder;
import de.symeda.sormas.app.component.visualization.ViewTypeHelper;
import de.symeda.sormas.app.component.visualization.data.SummaryCircularData;
import de.symeda.sormas.app.component.visualization.data.SummaryTotalData;
import de.symeda.sormas.app.core.adapter.multiview.IAdapterRegistrationContext;
import de.symeda.sormas.app.core.adapter.multiview.IAdapterRegistrationService;
import de.symeda.sormas.app.dashboard.SummaryObservableDataResult;
import de.symeda.sormas.app.util.ResourceUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Orson on 08/04/2018.
 * <p>
 * www.technologyboard.org
 * sampson.orson@gmail.com
 * sampson.orson@technologyboard.org
 */
public class SampleSummaryFragment extends BaseSummaryFragment<ViewTypeHelper.ViewTypeEnum, SampleSummaryAdapter> {

    public static final String TAG = SampleSummaryFragment.class.getSimpleName();

    private CompositeSubscription mSubscription = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        showPreloader();
        Subscription mDataSubscription = Observable
                .zip(getTotalDataObservable(), getCircularDataObservable(), getMergeDataObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SummaryObservableDataResult>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage(), e);
                        hidePreloader();
                        showEmptySummaryHint();
                    }

                    @Override
                    public void onNext(SummaryObservableDataResult summparyObservableDataResult) {
                        final List<SummaryTotalData> totalData = summparyObservableDataResult.getTotalData();
                        final List<SummaryCircularData> circularData = summparyObservableDataResult.getCircularData();

                        try {
                            getLandingAdapter().startConfig().forViewType(ViewTypeHelper.ViewTypeEnum.TOTAL, new IAdapterRegistrationService() {
                                @Override
                                public void register(IAdapterRegistrationContext context) throws java.lang.InstantiationException, IllegalAccessException {
                                    context.registerBinder(SummaryTotalBinder.class).registerData(totalData);
                                }
                            })
                                    .forViewType(ViewTypeHelper.ViewTypeEnum.SINGLE_CIRCULAR_PROGRESS, new IAdapterRegistrationService() {
                                        @Override
                                        public void register(IAdapterRegistrationContext context) throws java.lang.InstantiationException, IllegalAccessException {
                                            context.registerBinder(SummaryCircularProgressBinder.class).registerData(circularData);
                                        }
                                    });

                            hidePreloader();
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, e.getMessage(), e);
                        } catch (java.lang.InstantiationException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                });

        mSubscription.add(mDataSubscription);
        configure();
    }


    //<editor-fold desc="Observable Methods">
    private Observable<List<SummaryTotalData>> getTotalDataObservable() {
        return Observable.defer(new Func0<Observable<List<SummaryTotalData>>>() {
            @Override
            public Observable<List<SummaryTotalData>> call() {
                return Observable.create(new Observable.OnSubscribe<List<SummaryTotalData>>() {
                    @Override
                    public void call(Subscriber<? super List<SummaryTotalData>> subscriber) {
                        try {
                            subscriber.onNext(getTotalDataAsync());
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<List<SummaryCircularData>> getCircularDataObservable() {
        return Observable.defer(new Func0<Observable<List<SummaryCircularData>>>() {
            @Override
            public Observable<List<SummaryCircularData>> call() {
                return Observable.create(new Observable.OnSubscribe<List<SummaryCircularData>>() {
                    @Override
                    public void call(Subscriber<? super List<SummaryCircularData>> subscriber) {
                        try {
                            subscriber.onNext(getCircularDataAsync());
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Func2<List<SummaryTotalData>, List<SummaryCircularData>, SummaryObservableDataResult> getMergeDataObservable() {
        return new Func2<List<SummaryTotalData>, List<SummaryCircularData>, SummaryObservableDataResult>() {
            @Override
            public SummaryObservableDataResult call(List<SummaryTotalData> summaryTotalData, List<SummaryCircularData> summaryCircularData) {
                return new SummaryObservableDataResult(summaryTotalData, summaryCircularData);
            }
        };
    }
    //</editor-fold>

    //<editor-fold desc="Private Methods">
    private List<SummaryTotalData> getTotalDataAsync() {
        List<SummaryTotalData> dataSet = new ArrayList<>();

        SummaryTotalData data = new SummaryTotalData();
        data.dataTitle = ResourceUtils.getString(getActivity(), R.string.caption_total_samples);
        ;
        data.dataValue = String.valueOf(new Random().nextInt(10000));
        dataSet.add(data);

        return dataSet;
    }

    private List<SummaryCircularData> getCircularDataAsync() {
        Random random = new Random();
        List<SummaryCircularData> dataSet = new ArrayList<>();

        String titlePositiveResults = ResourceUtils.getString(getActivity(), R.string.caption_positive);
        String titleNegativeResults = ResourceUtils.getString(getActivity(), R.string.caption_negative);
        String titlePendingResults = ResourceUtils.getString(getActivity(), R.string.caption_pending);
        String titleIndeterminateResults = ResourceUtils.getString(getActivity(), R.string.caption_indeterminate);
        String titleInadequateSpecimen = ResourceUtils.getString(getActivity(), R.string.caption_inadequate_specimen);

        //Confirmed Cases
        SummaryCircularData data1 = new SummaryCircularData(titlePositiveResults, random.nextInt(10000), random.nextInt(100),
                R.color.circularProgressFinishedWatchOut, R.color.circularProgressUnfinishedWatchOut);

        //Probable Cases
        SummaryCircularData data2 = new SummaryCircularData(titleNegativeResults, random.nextInt(10000), random.nextInt(100),
                R.color.circularProgressFinishedSuccess, R.color.circularProgressUnfinishedSuccess);

        //Suspected Case
        SummaryCircularData data3 = new SummaryCircularData(titlePendingResults, random.nextInt(10000), random.nextInt(100),
                R.color.circularProgressFinishedWarning, R.color.circularProgressUnfinishedWarning);

        //Fatalities
        SummaryCircularData data4 = new SummaryCircularData(titleIndeterminateResults, random.nextInt(10000), random.nextInt(100), R.color.circularProgressFinishedNeutral,
                R.color.circularProgressUnfinishedNeutral);

        //Case Fatality Rate
        SummaryCircularData data5 = new SummaryCircularData(titleInadequateSpecimen, random.nextInt(10000), random.nextInt(100), R.color.circularProgressFinishedNeutral,
                R.color.circularProgressUnfinishedNeutral);

        dataSet.add(data1);
        dataSet.add(data2);
        dataSet.add(data3);
        dataSet.add(data4);
        dataSet.add(data5);

        return dataSet;
    }
    //</editor-fold>

    //<editor-fold desc="More Overrides">
    @Override
    protected int getSectionTitleResId() {
        return R.string.heading_sample_summary;
    }

    @Override
    protected int getEntityResId() {
        return R.string.entity_sample;
    }

    @Override
    protected SampleSummaryAdapter createSummaryAdapter() {
        return new SampleSummaryAdapter(getActivity());
    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
    }

    @Override
    protected int getContainerResId() {
        return R.id.fragment_frame_sample;
    }

    @Override
    public String getIdentifier() {
        return TAG;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mSubscription != null && !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }
    //</editor-fold>

    public static SampleSummaryFragment newInstance() {
        return newInstance(SampleSummaryFragment.class, null);
    }
}