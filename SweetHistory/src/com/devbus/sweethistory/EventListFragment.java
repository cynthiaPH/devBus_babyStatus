package com.devbus.sweethistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.devbus.controller.Events;
import com.devbus.controller.Tips;
import com.devbus.data.Event;
import com.devbus.data.Tip;
import com.devbus.sweethistory.TipListFragment.TipArrayAdapter;

public class EventListFragment extends ListFragment {

	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(this.generateAdapter());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View timeLineView = inflater.inflate(R.layout.time_line, null);
		final TextView txtCalendar = (TextView) timeLineView
				.findViewById(R.id.txtCalendar);
		txtCalendar.setText(formatter.format(new Date()));
		txtCalendar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDateDialog(txtCalendar);
			}
		});

		return timeLineView;
	}


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), FormElementActivity.class);
        intent.putExtra("TYPE_ELEMENT","event");
        intent.putExtra("ELEMENT_ID",id);
        startActivity(intent);

    }

	private void showDateDialog(final TextView txtView) {
		// create a new dialog fragment
		DialogFragment newFragment;
		try {
			// Gets a new instance of the DatePickerFragment using the
			// actual not after date and create a new DataSetListener
			newFragment = DatePickerFragment.newInstance(
					formatter.parse(txtView.getText().toString()),
					new DatePickerFragment.OnDateSetListener() {
						@Override
						public void onDateSet(Date date) {
							txtView.setText(formatter.format(date));
						}
					}, true);
			newFragment.show(getFragmentManager(), "datePicker");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private ArrayAdapter<Event> generateAdapter() {
		Events events = new Events();
		events.fillList();

		// ArrayAdapter<Person> adapter = new
		// ArrayAdapter<Person>(this.getActivity(),
		// R.layout.row_person,R.id.txvPersonName,persons.getPersons());
		ArrayAdapter<Event> adapter = new TipArrayAdapter(this.getActivity(),
				R.layout.row_tip, events.getEvents());

		return adapter;
	}

	// Create a custimized adapter
	public class TipArrayAdapter extends ArrayAdapter<Event> {

		public TipArrayAdapter(Context context, int resource,
				List<Event> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowElement = inflater.inflate(R.layout.row_tip, null);

			TextView elementTitle = (TextView) rowElement
					.findViewById(R.id.tipTitle);
			elementTitle.setText(this.getItem(position).getDate());
			LinearLayout circleElement = (LinearLayout) rowElement
					.findViewById(R.id.backTimeLineCircle);
			circleElement.setBackgroundResource(R.drawable.circle_evento_state);
			ImageView iconTimeLineCircle = (ImageView) rowElement
					.findViewById(R.id.iconTimeLineCircle);
			iconTimeLineCircle.setImageResource(R.drawable.evento);

			return rowElement;
		}

	}

}