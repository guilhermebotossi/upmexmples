package br.inpe.climaespacial.swd.commons.services;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;

import java.io.IOException;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.adapters.DownloadAdapter;
import br.inpe.climaespacial.swd.commons.exceptions.DownloadException;

@Dependent
public class StringDownloader implements Downloader {

    @Inject
    private DownloadAdapter downloadAdapter;

    public String download(String url) {

        throwIfNull(url, "url");

        try {
            return downloadAdapter.download(url);
        } catch (IOException e) {
            throw createCustom("Erro ao efetuar download.", DownloadException.class);
        }
    }

}
