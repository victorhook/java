
import re

keywords = []

for file_ in range(1, 8):
    with open(f'rss/{file_}') as f:
        data = f.read()

    keywords.append(re.findall(r'(?<=<)[a-zA-Z]+(?=>)', data))


from itertools import chain

words = list(chain.from_iterable(keywords))

with open('keywords.txt', 'w') as f:
    f.write(str(set(words)))


    # {'language', 'uri', 'url', 'author', 'description',
    #         'copyright', 'image', 'script', 'title', 'icon',
    #         'name', 'webMaster', 'p', 'updated', 'ttl', 'category',
    #         'pubDate', 'published', 'link', 'channel', 'id', 'generator',
    #         'managingEditor', 'subtitle', 'item', 'lastBuildDate', 'entry',
    #         'docs', 'guid', 'comments'}

    private static final ArrayList<String> KEYWORDS = new ArrayList<>(Arrays.asList(new String[]{
            "rss", "channel", "title", "description", "language",
            "rating", "copyright", "pubDate", "lastBuildDate",
            "generator", "docs", "cloud", "ttl", "managingEditor",
            "webMaster", "skipHours", "image", "url", "link",
            "width", "height", "category", "author", "source",
            "item", "textInput", "name", "hour", "day", "guid",
            "skipDays", "comments", "enclosure", "atom:link",
            "uri", "icon", "updated", "subtitle", "entry"}));
