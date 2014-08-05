#!/usr/bin/python

from bs4 import BeautifulSoup

import requests
import posixpath
import urlparse
import datetime
import os


def get_links(linkfile, url):
    r = requests.get(url)
    data = r.text
    soup = BeautifulSoup(data)
    with open(linkfile, 'w') as f:
        for alink in soup.find_all('a'):
            link = alink.get('href')
            f.write(link + '\n')


def process_links(links_file, basepath):
    lines = [line.strip() for line in open(links_file)]
    count = 1

    print "Getting Files..."

    for link in lines:
        path = urlparse.urlsplit(link).path
        filename = posixpath.basename(path)
        if 'FHR' in filename:
            try:
                target = basepath + filename
                print target
                if not os.path.exists(target):
                    get_file(link, target)
                else:
                    print "[SKIPPED] " + target

            except Exception, e:
                print "error fetching " + link
                print str(e)

            print('%4s' % count + " " + link)
            count += 1


def get_file(link, filename):
    with open(filename, 'wb') as handle:
        response = requests.get(link, stream=True)

        if not response.ok:
            print "error fetching " + link

        for block in response.iter_content(1024):
            if not block:
                break

            handle.write(block)


if __name__ == '__main__':
    url = "http://ratings.food.gov.uk/open-data/en-GB"
    DATA_STORE = "../FHM/"
    today = datetime.date.today()
    path = DATA_STORE + str(today) + "/"

    if not os.path.exists(path):
        os.mkdir(path)

    linkfile = path + "links.txt"

    if not os.path.exists(linkfile):
        get_links(linkfile, url)

    process_links(linkfile, path)
