package org.usf.trace.api.server.controller;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.usf.trace.api.server.config.TraceApiColumn.ADDRESS;
import static org.usf.trace.api.server.config.TraceApiColumn.API_NAME;
import static org.usf.trace.api.server.config.TraceApiColumn.APP_NAME;
import static org.usf.trace.api.server.config.TraceApiColumn.AUTH;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_ERROR;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_ERROR_CLIENT;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_ERROR_SERVER;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_FAST;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_FASTEST;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_MEDIUM;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_SLOW;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_SLOWEST;
import static org.usf.trace.api.server.config.TraceApiColumn.COUNT_SUCCES;
import static org.usf.trace.api.server.config.TraceApiColumn.ELAPSEDTIME;
import static org.usf.trace.api.server.config.TraceApiColumn.END;
import static org.usf.trace.api.server.config.TraceApiColumn.ENVIRONEMENT;
import static org.usf.trace.api.server.config.TraceApiColumn.HOST;
import static org.usf.trace.api.server.config.TraceApiColumn.MEDIA;
import static org.usf.trace.api.server.config.TraceApiColumn.METHOD;
import static org.usf.trace.api.server.config.TraceApiColumn.OS;
import static org.usf.trace.api.server.config.TraceApiColumn.PATH;
import static org.usf.trace.api.server.config.TraceApiColumn.PORT;
import static org.usf.trace.api.server.config.TraceApiColumn.PROTOCOL;
import static org.usf.trace.api.server.config.TraceApiColumn.QUERY;
import static org.usf.trace.api.server.config.TraceApiColumn.RE;
import static org.usf.trace.api.server.config.TraceApiColumn.SIZE_IN;
import static org.usf.trace.api.server.config.TraceApiColumn.SIZE_OUT;
import static org.usf.trace.api.server.config.TraceApiColumn.START;
import static org.usf.trace.api.server.config.TraceApiColumn.STATUS;
import static org.usf.trace.api.server.config.TraceApiColumn.THREAD;
import static org.usf.trace.api.server.config.TraceApiColumn.USER;
import static org.usf.trace.api.server.config.TraceApiColumn.VERSION;
import static org.usf.trace.api.server.config.TraceApiTable.APIREQUEST;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.usf.trace.api.server.metadata.CombinedFieldMetadata;
import org.usf.trace.api.server.metadata.FieldMetadata;
import org.usf.trace.api.server.metadata.SimpleFieldMetadata;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "metadata", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MetadataController {

    @GetMapping("aggregate")
    List<FieldMetadata> fetch() {
        return asList(
                new SimpleFieldMetadata(APIREQUEST, ELAPSEDTIME, ELAPSEDTIME.reference(), "temps de réponse (s)", "s"),
                new CombinedFieldMetadata("nombre d'appels OK / KO", asList(
                        new SimpleFieldMetadata(APIREQUEST, COUNT_ERROR, COUNT_ERROR.reference(), "nombre d'appels en erreur", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_SUCCES, COUNT_SUCCES.reference(), "nombre d'appels OK", "count"))),

                new CombinedFieldMetadata("nombre d'appels 2xx/4xx/5xx", asList(
                        new SimpleFieldMetadata(APIREQUEST, COUNT_ERROR_CLIENT, COUNT_ERROR_CLIENT.reference(), "nombre d'appels en erreur client", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_ERROR_SERVER, COUNT_ERROR_SERVER.reference(), "nombre d'appels en erreur serveur", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_SUCCES, COUNT_SUCCES.reference(), "nombre d'appels OK", "c°"))),

                new CombinedFieldMetadata("nombre d'appels par temps de réponse", asList(
                        new SimpleFieldMetadata(APIREQUEST, COUNT_SLOWEST, COUNT_SLOWEST.reference(), "temps de réponse les plus lents ", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_SLOW, COUNT_SLOW.reference(), "temps de réponse lent", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_MEDIUM, COUNT_MEDIUM.reference(), "temps de réponse moyen", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_FAST, COUNT_FAST.reference(), "temps de réponse rapide", "count"),
                        new SimpleFieldMetadata(APIREQUEST, COUNT_FASTEST, COUNT_FASTEST.reference(), "temps de réponse les plus rapides", "count")))
        );
    }

    @GetMapping("filter")
    List<SimpleFieldMetadata> fetchFilters() {
        return asList(

                new SimpleFieldMetadata(APIREQUEST, METHOD, METHOD.reference(), "Methode", "count"),
                new SimpleFieldMetadata(APIREQUEST, PROTOCOL, PROTOCOL.reference(), "Protocole", "count"),
                new SimpleFieldMetadata(APIREQUEST, HOST, HOST.reference(), "Hôte", "count"),
                new SimpleFieldMetadata(APIREQUEST, PORT, PORT.reference(), "Port", "count"),
                new SimpleFieldMetadata(APIREQUEST, PATH, PATH.reference(), "Path", "count"),
                new SimpleFieldMetadata(APIREQUEST, QUERY, QUERY.reference(), "Query", "count"),
                new SimpleFieldMetadata(APIREQUEST, MEDIA, MEDIA.reference(), "Content-Type", "count"),
                new SimpleFieldMetadata(APIREQUEST, AUTH, AUTH.reference(), "Schéma d'authentification", "count"),
                new SimpleFieldMetadata(APIREQUEST, STATUS, STATUS.reference(), "Status", "count"),
                new SimpleFieldMetadata(APIREQUEST, SIZE_IN, SIZE_IN.reference(), "Taille d'entrée", "count"),
                new SimpleFieldMetadata(APIREQUEST, SIZE_OUT, SIZE_OUT.reference(), "Taille de sortie", "count"),
                new SimpleFieldMetadata(APIREQUEST, START, START.reference(), "Date de début", "count"),
                new SimpleFieldMetadata(APIREQUEST, END, END.reference(), "Date de fin", "count"),
                new SimpleFieldMetadata(APIREQUEST, THREAD, THREAD.reference(), "Thread", "count"),
                new SimpleFieldMetadata(APIREQUEST, API_NAME, API_NAME.reference(), "Nom d'Api", "count"),
                new SimpleFieldMetadata(APIREQUEST, USER, USER.reference(), "Utilisateur", "count"),
                new SimpleFieldMetadata(APIREQUEST, APP_NAME, APP_NAME.reference(), "Nom d'application", "count"),
                new SimpleFieldMetadata(APIREQUEST, VERSION, VERSION.reference(), "Version", "count"),
                new SimpleFieldMetadata(APIREQUEST, ADDRESS, ADDRESS.reference(), "Adresse", "count"),
                new SimpleFieldMetadata(APIREQUEST, ENVIRONEMENT, ENVIRONEMENT.reference(), "Environement", "count"),
                new SimpleFieldMetadata(APIREQUEST, OS, OS.reference(), "Sytèm d'exploitation", "count"),
                new SimpleFieldMetadata(APIREQUEST, RE, RE.reference(), "Environement d'exécution", "count")
        );
    }
}
