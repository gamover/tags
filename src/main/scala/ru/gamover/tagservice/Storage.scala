package ru.gamover.tagservice

import scala.collection.mutable.ArrayBuffer

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */
/**removeEntry
 * Запись
 * @param id   идентификатор записи
 * @param name название записи
 */
case class Entry(id  : Long,
                 name: String)

/**
 * Тег
 * @param id   идентификатор тега
 * @param name имя тега
 */
case class Tag(id  : Long,
               name: String)

/**
 * Связка запись-тег
 * @param id      идентификатор связки
 * @param entryId идентификатор записи
 * @param tagId   идентификатор тега
 */
case class EntryTag(id     : Long,
                    entryId: Long,
                    tagId  : Long)

/**
 * Хранилище
 */
class Storage {
  private var entriesSeq    : Long = 0l
  private var tagsSeq       : Long = 0l
  private var entriesTagsSeq: Long = 0l

  val entries    : ArrayBuffer[Entry]    = ArrayBuffer.empty
  val tags       : ArrayBuffer[Tag]      = ArrayBuffer.empty
  val entriesTags: ArrayBuffer[EntryTag] = ArrayBuffer.empty

  /**
   * Добавление записи
   * @param name имя записи
   * @return
   */
  def addEntry(name: String): Long = {
    if (!entries.exists(_.name.equalsIgnoreCase(name))) {
      entriesSeq += 1l
      entries += Entry(entriesSeq, name)
      return entriesSeq
    }
    -1l
  }

  /**
   * Удаление записи по идентификатору
   * @param id идентификатор записи
   */
  def removeEntry(id: Long): Unit = {
    val index = entries.indexWhere(_.id == id)
    if (index > -1) entries.remove(index)
    ()
  }

  /**
   * Получение записи по идентификатору
   * @param id идентификатор записи
   * @return
   */
  def getEntryById(id: Long): Option[Entry] = {
    entries.find(_.id == id)
  }

  /**
   * Получение списка записей по списку тегов
   * @param tagIdList список идентификаторов тегов
   * @return
   */
  def getEntriesByTags(tagIdList: List[Long]): List[Entry] = {
    entriesTags
      .filter(entryTag => tagIdList.contains(entryTag.tagId))
      .map(_.entryId)
      .distinct
      .map(getEntryById)
      .filter(_.isDefined)
      .map(_.get).toList
  }

  /**
   * Добавление тега
   * @param name имя тега
   * @return
   */
  def addTag(name: String): Long = {
    if (!tags.exists(_.name.equalsIgnoreCase(name))) {
      tagsSeq += 1l
      tags += Tag(tagsSeq, name)
      return tagsSeq
    }
    -1l
  }

  /**
   * Удаление тега по идентификатору
   * @param id идентификатор тега
   */
  def removeTag(id: Long): Unit = {
    val index = tags.indexWhere(_.id == id)
    if (index > -1) tags.remove(index)
    ()
  }

  /**
   * Получение тега по идентификатору
   * @param id идентификатор тега
   * @return
   */
  def getTagById(id: Long): Option[Tag] = {
    tags.find(_.id == id)
  }

  /**
   * Получение списка тегов по идентификатору записи
   * @param entryId идентификатор записи
   * @return
   */
  def getTagsByEntryId(entryId: Long): List[Tag] = {
    entriesTags
      .filter(_.entryId == entryId)
      .map(entryTag => getTagById(entryTag.tagId))
      .filter(_.isDefined)
      .map(_.get).toList
  }

  /**
   * Добавление связки запись-тег
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   * @return
   */
  def addEntryTag(entryId: Long, tagId: Long): Long = {
    if (!entriesTags.exists(entryTag => entryTag.entryId == entryId && entryTag.tagId == tagId)) {
      entriesTagsSeq += 1l
      entriesTags += EntryTag(entriesTagsSeq, entryId, tagId)
      return entriesTagsSeq
    }
    -1l
  }

  /**
   * Удаление связки запись-тег по идентификатору
   * @param id идентификатор связки
   */
  def removeEntryTagById(id: Long): Unit = {
    val index = entriesTags.indexWhere(_.id == id)
    if (index > -1) entriesTags.remove(index)
    ()
  }

  /**
   * Удаление связки запись-тег по идентификатору записи и идентификатору тега
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   */
  def removeTagFromEntry(entryId: Long, tagId: Long): Unit = {
    val index = entriesTags.indexWhere(entryTag => entryTag.entryId == entryId && entryTag.tagId == tagId)
    if (index > -1) entriesTags.remove(index)
    ()
  }
}

object Storage {
  implicit def entry2thriftEntry(entry: Entry): thriftscala.Entry = thriftscala.Entry(id = entry.id, name = entry.name)
  implicit def thriftEntry2Entry(thriftEntry: thriftscala.Entry): Entry = Entry(id = thriftEntry.id, name = thriftEntry.name)
  implicit def tag2thriftTag(tag: Tag): thriftscala.Tag = thriftscala.Tag(id = tag.id, name = tag.name)
  implicit def thriftTag2Tag(thriftTag: thriftscala.Tag): Tag = Tag(id = thriftTag.id, name = thriftTag.name)
  implicit def listEntry2listThriftEntry(list: List[Entry]): List[thriftscala.Entry] = list.map(entry => thriftscala.Entry(id = entry.id, name = entry.name))
  implicit def listThriftEntry2ListEntry(list: List[thriftscala.Entry]): List[Entry] = list.map(thriftEntry => Entry(id = thriftEntry.id, name = thriftEntry.name))
  implicit def listTag2listThriftTag(list: List[Tag]): List[thriftscala.Tag] = list.map(tag => thriftscala.Tag(id = tag.id, name = tag.name))
  implicit def listThriftTag2ListTag(list: List[thriftscala.Tag]): List[Tag] = list.map(thriftTag => Tag(id = thriftTag.id, name = thriftTag.name))
}