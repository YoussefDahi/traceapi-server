package org.usf.trace.api.server.model.filter;

import lombok.Getter;
import lombok.Setter;
import org.usf.jquery.core.DBFilter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.usf.jquery.core.Utils.isEmpty;
import static org.usf.trace.api.server.config.TraceApiColumn.*;
import static org.usf.trace.api.server.config.TraceApiTable.REQUEST;
import static org.usf.trace.api.server.config.TraceApiTable.SESSION;

@Getter
@Setter
public class JqueryMainSessionFilter extends JquerySessionFilter {
    private final String[] names;
    private final String[] launchModes;
    private final String location;

    public JqueryMainSessionFilter(String[] ids, String[] appNames, String[] environments, Instant start, Instant end, boolean lazy, String[] names, String[] launchModes, String location) {
        super(ids, appNames, environments, start, end, lazy);
        this.names = names;
        this.launchModes = launchModes;
        this.location = location;
    }

    public JqueryMainSessionFilter(String[] ids, boolean lazy) {
        this(ids, null, null, null, null, lazy, null, null, null);
    }

    public DBFilter[] filters() {
        List<DBFilter> filters = new ArrayList<>();
        if(!isEmpty(getIds())) {
            filters.add(SESSION.column(ID).in(getIds()));
        }
        if(!isEmpty(getNames())) {
            filters.add(SESSION.column(NAME).in(getNames()));
        }
        if(!isEmpty(getLaunchModes())) {
            filters.add(SESSION.column(TYPE).in(getLaunchModes()));
        }
        if(getLocation() != null) {
            filters.add(REQUEST.column(METHOD).like(getLocation()));
        }
        if(getStart() != null) {
            filters.add(REQUEST.column(START).greaterOrEqual(getStart()));
        }
        if(getEnd() != null) {
            filters.add(REQUEST.column(END).lessThan(getEnd()));
        }
        return filters.toArray(DBFilter[]::new);
    }
}
