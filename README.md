## DateRanger

The DateRanger is a little JavaFX control which can either be used to simply visualize a given
month and year or to select ranges defined by a start- and end-date.

The default view shows the current month and year with the current day selected (light blue).
You can select a date by clicking on it.
The control has properties to get the selectedDate, the currentMonth and the currentYear.
And you can also set those properties.
On the left side you see the ISO calendar week and on top the short form for the days of
the week (depending on the locale that you also can set).
The DateRanger itself comes without the header that you see on the following screenshots
which means if you would like to have a label that shows the current month with the year
and some buttons to switch between months you either can create your own ones or use
the DateRangerControl which creates exactly this control that you see on the screenshots 
below.

Selected date:

![SelectedDate](https://i.ibb.co/tsMbYbq/Date-Ranger-1.png)

Selected range:
If you hold down the `SHIFT` key, you can select a range of dates.

![SelectedRange](https://i.ibb.co/7WbrqKm/Date-Ranger-2.png)

If you embedd the DateRanger in your own control and would like to use the selection, 
you have to forward MouseEvents to the DateRanger by forwarding them to the dateRanger.onMousePressed(MouseEvent evt)
method. To see how you can do this you might want to take a look at the `DateRangerControl.java` file.

The DateRanger control is styleable using css, please find the applied styles in the
`date-ranger.css` file.