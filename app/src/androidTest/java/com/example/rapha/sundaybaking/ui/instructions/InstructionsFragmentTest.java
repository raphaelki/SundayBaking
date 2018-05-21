package com.example.rapha.sundaybaking.ui.instructions;

import android.arch.lifecycle.MutableLiveData;
import android.os.SystemClock;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.rapha.sundaybaking.R;
import com.example.rapha.sundaybaking.TwoFrameFragmentTestingActivity;
import com.example.rapha.sundaybaking.data.models.InstructionStep;
import com.example.rapha.sundaybaking.util.DataUtil;
import com.example.rapha.sundaybaking.util.EspressoTestUtil;
import com.example.rapha.sundaybaking.util.StringUtil;
import com.example.rapha.sundaybaking.util.ViewModelUtil;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.rapha.sundaybaking.util.OrientationChangeAction.orientationLandscape;
import static com.example.rapha.sundaybaking.util.OrientationChangeAction.orientationPortrait;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for InstructionsFragment and PlayerFragment
 * Some tests might be flaky on emulator devices because Espresso does not wait for
 * screen rotation and ViewPager navigation to finish properly even with animations turned off in
 * developer options
 */
@RunWith(AndroidJUnit4.class)
public class InstructionsFragmentTest {

    private final String VIDEO_TEST_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";
    private final String RECIPE_NAME = "Apple pie";
    @Rule
    public final ActivityTestRule<TwoFrameFragmentTestingActivity> activityTestRule =
            new ActivityTestRule<>(TwoFrameFragmentTestingActivity.class);
    private final int requestedStepOnStartup = 0;
    private final MutableLiveData<InstructionStep> step = new MutableLiveData<>();
    private final MutableLiveData<String> videoURL = new MutableLiveData<>();
    private final MutableLiveData<List<InstructionStep>> steps = new MutableLiveData<>();
    private final MutableLiveData<Boolean> deviceIsConnected = new MutableLiveData<>();

    @Mock
    private SharedViewModel viewModel;

    @Captor
    private ArgumentCaptor<Integer> stepCaptor;

    @Before
    public void initialization() {
        EspressoTestUtil.disableAnimations(activityTestRule);
        EspressoTestUtil.changeOrientationToPortrait(activityTestRule.getActivity());

        MockitoAnnotations.initMocks(this);
        when(viewModel.getSelectedStep()).thenReturn(step);
        when(viewModel.getVideoUrl()).thenReturn(videoURL);
        when(viewModel.getInstructionSteps()).thenReturn(steps);
        when(viewModel.getConnectionAvailability()).thenReturn(deviceIsConnected);
        deviceIsConnected.postValue(true);

        PlayerFragment playerFragment = PlayerFragment.forRecipe(RECIPE_NAME);
        playerFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        InstructionsFragment instructionsFragment = InstructionsFragment.forRecipe(RECIPE_NAME, requestedStepOnStartup);
        instructionsFragment.viewModelFactory = ViewModelUtil.createFor(viewModel);
        activityTestRule.getActivity().setFragments(playerFragment, instructionsFragment);
    }

    @Test
    public void openPlayerWithEmptyVideoURL_displaysPlaceholder() {
        setupStepTestData(1);
        onView(withId(R.id.no_video_placeholder_iv)).check(matches(isDisplayed()));
    }

    @Test
    public void openPlayerWithVideoURL_displaysVidePlayer() {
        InstructionStep stepData = DataUtil.createDirectionStepWithVideoUrl(VIDEO_TEST_URL);
        step.postValue(stepData);
        videoURL.postValue(VIDEO_TEST_URL);
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    @Test
    public void stepsAreSet_changesStepInViewModel() {
        setupStepTestData(6);
        onView(withId(R.id.instructions_view_pager)).check(matches(isDisplayed()));
        verify(viewModel).changeCurrentStep(stepCaptor.capture());
        assertThat(stepCaptor.getValue(), is(requestedStepOnStartup));
    }

    @Test
    public void swipeLeftOnDirections_changesToNextStep() {
        List<InstructionStep> stepList = setupStepTestData(2);
        verifyStepIsDisplayed(stepList.get(0));
        onView(withId(R.id.instructions_view_pager)).perform(ViewActions.swipeLeft());
        verify(viewModel, times(2)).changeCurrentStep(stepCaptor.capture());
        assertThat(stepCaptor.getValue(), is(stepList.get(1).getStepNo()));
        verifyStepIsDisplayed(stepList.get(1));
    }

    private void verifyStepIsDisplayed(InstructionStep step) {
        onView(withText(StringUtil.prepareShortDescription(step))).check(matches(isDisplayed()));
    }

    @Test
    public void changeOrientation_checkPlayerVisibility() {
        InstructionStep stepData = DataUtil.createDirectionStepWithVideoUrl(VIDEO_TEST_URL);
        // switch orientation
        changeOrientationAndCheckPlayerVisibility(stepData, orientationLandscape());
        // switch back orientation
        changeOrientationAndCheckPlayerVisibility(stepData, orientationPortrait());
    }

    @Test
    public void rotateScreen_stepNoSurvivesOrientationChange() {
        int desiredStep = 3;
        // create 6 steps
        List<InstructionStep> stepList = DataUtil.createDirectionSteps(6);
        // set steps to live data
        steps.postValue(stepList);
        // swipe two times on view pager to display third step
        for (int index = 0; index < desiredStep; index++) {
            onView(withId(R.id.instructions_view_pager)).perform(ViewActions.swipeLeft());
        }
        // verify that the step change is forwarded to the view model
        verify(viewModel, times(desiredStep + 1)).changeCurrentStep(stepCaptor.capture());
        // verify that the correct text is displayed
        verifyStepIsDisplayed(stepList.get(desiredStep));
        // change orientation
        onView(isRoot()).perform(orientationLandscape());
        // wait 1 second
        SystemClock.sleep(1000);
        // verify that ViewPager is not shown in landscape mode
        onView(withId(R.id.instructions_view_pager)).check(matches(not(isDisplayed())));
        // change back orientation
        onView(isRoot()).perform(orientationPortrait());
        // set step list
        steps.postValue(stepList);
        // verify instructions are shown
        onView(withId(R.id.instructions_view_pager)).check(matches(isDisplayed()));
        // verify correct step number is requested from the ViewModel
        ArgumentCaptor<Integer> requestedStepCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(viewModel, atLeastOnce()).changeCurrentStep(requestedStepCaptor.capture());
        int requestedStep = requestedStepCaptor.getValue();
        assertThat(requestedStep, is(stepCaptor.getValue()));
        // post value and verify step is shown in ViewPager
        step.postValue(stepList.get(requestedStep));
        // ugly workaround to wait for the ViewPager to be displayed. Approaches using IndlingResource
        // and listen to the ViewPagers state didn't work
        SystemClock.sleep(1000);
        verifyStepIsDisplayed(stepList.get(desiredStep));
    }

    private void changeOrientationAndCheckPlayerVisibility(InstructionStep stepData, ViewAction orientationChangeAction) {
        onView(isRoot()).perform(orientationChangeAction);
        step.postValue(stepData);
        videoURL.postValue(VIDEO_TEST_URL);
        onView(withId(R.id.player_view)).check(matches(isDisplayed()));
    }

    private List<InstructionStep> setupStepTestData(int stepCount) {
        List<InstructionStep> stepsList = DataUtil.createDirectionSteps(stepCount);
        steps.postValue(stepsList);
        return stepsList;
    }
}
