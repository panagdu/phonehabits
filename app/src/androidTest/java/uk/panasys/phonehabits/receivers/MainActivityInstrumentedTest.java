package uk.panasys.phonehabits.receivers;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.panasys.phonehabits.R;
import uk.panasys.phonehabits.activities.MainActivity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private MainActivity mTestActivity;


    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        mTestActivity = rule.getActivity();

    }

    @Test
    public void whenActivityStartsTheViewsAreInitialized() {
        TextView checkedYourPhone = mTestActivity.findViewById(R.id.greeting);

        assertThat(checkedYourPhone.getText().toString(), is("You've checked your phone"));
    }
}
