package net.fortytwo.smsn.monitron.listeners.sensors;

import net.fortytwo.smsn.monitron.Context;
import net.fortytwo.smsn.monitron.data.GaussianData;
import net.fortytwo.smsn.monitron.events.MonitronEvent;
import net.fortytwo.smsn.monitron.events.RelativeHumidityObservation;
import org.openrdf.model.IRI;

public class HygrometerListener extends GaussianSensorListener {

    public HygrometerListener(final Context context, final IRI sensor) {
        super(context, sensor);
    }

    protected MonitronEvent handleSample(final GaussianData data) {
        return new RelativeHumidityObservation(context, sensor, data);
    }
}
