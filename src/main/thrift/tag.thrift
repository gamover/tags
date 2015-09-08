namespace java ru.gamover.tagservice.thriftjava
#@namespace scala ru.gamover.tagservice.thriftscala

typedef i64 long

struct Entry {
  1: long id;
  2: string name;
}

struct Tag {
  1: long id;
  2: string name;
}

service TagService {
  long addEntry(1:string name);
  void removeEntry(1:long id);
  list<Entry> getEntryById(1: long id);
  list<Entry> getEntriesByTags(1: list<long> tagIdList);

  long addTag(1:string name);
  void removeTag(1:long id);
  list<Tag> getTagById(1: long id);
  list<Tag> getEntryTags(1: long entryId);

  long addTagToEntry(1: long entryId, 2: long tagId);
  void removeTagFromEntry(1: long entryId, 2: long tagId);
}