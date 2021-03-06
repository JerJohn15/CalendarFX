/**
 * Copyright (C) 2015, 2016 Dirk Lemmermann Software & Consulting (dlsc.com) 
 * 
 * This file is part of CalendarFX.
 */

package com.calendarfx.view;

import com.calendarfx.model.Entry;
import com.calendarfx.util.Util;
import com.google.ical.values.RRule;
import impl.com.calendarfx.view.RecurrenceViewSkin;
import javafx.beans.property.*;
import javafx.scene.control.Skin;

import java.text.ParseException;
import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

/**
 * A custom control used for editing recurrence rules according to RFC 2445. The
 * image below shows the four configurations of the control depending on the
 * currently selected frequency (daily, weekly, monthly, yearly).
 * <p/>
 * <center><img width="80%" src="doc-files/recurrence-view.png"></center>
 * <p/>
 */
public class RecurrenceView extends CalendarFXControl {

    /**
     * Constructs a new recurrence view.
     */
    public RecurrenceView() {
        getStyleClass().add("recurrence-view"); //$NON-NLS-1$
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new RecurrenceViewSkin(this);
    }

    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<LocalDate>(
            this, "startDate", LocalDate.now()) { //$NON-NLS-1$
        @Override
        public void set(LocalDate newValue) {
            requireNonNull(newValue);
            super.set(newValue);
        }
    };

    /**
     * A property used to store the start date for the recurrence rule. The
     * start date is relevant when creating rules that specify an entry to
     * occure on a specific day of the month or the week.
     *
     * @return the start date
     */
    public final ObjectProperty<LocalDate> startDateProperty() {
        return startDate;
    }

    /**
     * Sets the value of {@link #startDateProperty()}.
     *
     * @param date
     *            the new start date
     */
    public final void setStartDate(LocalDate date) {
        requireNonNull(date);
        startDateProperty().set(date);
    }

    /**
     * Returns the value of {@link #startDateProperty()}.
     *
     * @return the start date for the rule
     */
    public final LocalDate getStartDate() {
        return startDateProperty().get();
    }

    private final StringProperty recurrenceRule = new SimpleStringProperty(this,
            "recurrenceRule", "RRULE:FREQ=DAILY") { //$NON-NLS-1$ //$NON-NLS-2$
        @Override
        public void set(String newValue) {
            try {
                if (newValue != null) {
                    new RRule(newValue);
                }
                super.set(newValue);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Stores the recurrence rule according to RFC 2445 syntax (e.g.
     * "RRULE:FREQ=DAILY").
     *
     * @see Entry#recurrenceRuleProperty()
     *
     * @return the recurrence rule
     */
    public final StringProperty recurrenceRuleProperty() {
        return recurrenceRule;
    }

    /**
     * Sets the value of {@link #recurrenceRuleProperty()}.
     *
     * @param rule
     *            the recurrence rule (e.g. "RRULE:FREQ=DAILY").
     */
    public final void setRecurrenceRule(String rule) {
        recurrenceRuleProperty().set(rule);
    }

    /**
     * Returns the value of {@link #recurrenceRuleProperty()}.
     *
     * @return the recurrence rule (e.g. "RRULE:FREQ=DAILY").
     */
    public final String getRecurrenceRule() {
        return recurrenceRuleProperty().get();
    }

    private final BooleanProperty showSummary = new SimpleBooleanProperty(this,
            "showSummary", true); //$NON-NLS-1$

    /**
     * A property used to control the visibility of the "summary" label. The
     * label interprets the recurrence rule and displays it in a human readable
     * format, e.g. "Every week on Saturday and Sunday", or
     * "Every 4 days, 5 times").
     *
     * @see Util#convertRFC2445ToText(String, LocalDate)
     *
     * @return true if the summary label should be shown
     */
    public final BooleanProperty showSummaryProperty() {
        return showSummary;
    }

    /**
     * Returns the value of {@link #showSummaryProperty()}.
     *
     * @return true if the summary label will be shown
     */
    public final boolean isShowSummary() {
        return showSummaryProperty().get();
    }

    /**
     * Sets the value of {@link #showSummaryProperty()}.
     *
     * @param show
     *            if true the summary label will be shown
     */
    public final void setShowSummary(boolean show) {
        showSummaryProperty().set(show);
    }
}
