package ru.gamover.tagservice

import com.twitter.finagle.Thrift
import com.twitter.util.Await
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

/**
 * Created by G☠mOBEP [ 08.09.15 ]
 */
class ServiceTest extends FlatSpec with Matchers with BeforeAndAfterAll {
  val client = new Client

  override def beforeAll(): Unit = {
    // запуск сервера для тестов
    new Thread {
      override def run(): Unit = {
        val server = Thrift.serveIface("localhost:8080", new TagServiceImpl(new Storage))
        Await.ready(server)
      }
    }.start()
  }

  "Service" should "add/get/remove entry" in {
    val entryName = "entry"

    // проверка добавления записи
    val entryId = Await.result(client.addEntry(entryName))
    entryId should be(1l)

    // проверка получения записи
    val firstEntry = Await.result(client.getEntryById(entryId))
    firstEntry should not be None
    firstEntry.get.id should be(1l)
    firstEntry.get.name should be(entryName)

    // проверка удаления записи
    Await.result(client.removeEntry(entryId))
    Await.result(client.getEntryById(entryId)) should be(None)
  }

  "Service" should "add/get/remove tag" in {
    val tagName = "tag"

    // проверка добавления тега
    val tagId = Await.result(client.addTag(tagName))
    tagId should be(1l)

    // проверка получения тега
    val firstTag = Await.result(client.getTagById(tagId))
    firstTag should not be None
    firstTag.get.id should be(1l)
    firstTag.get.name should be(tagName)

    // проверка удаления тега
    Await.result(client.removeTag(tagId))
    Await.result(client.getTagById(tagId)) should be(None)
  }

  "Service" should "add/remove tag to/from entry" in {
    val entryName = "second entry"
    val tagName = "second tag"

    // создание записи и тега
    val entryId = Await.result(client.addEntry(entryName))
    val tagId = Await.result(client.addTag(tagName))
    Await.result(client.addTagToEntry(entryId, tagId))

    // проверка добавления тега к записи
    val tag = Await.result(client.getEntryTags(entryId)).find(_.id == tagId)
    tag should be('defined)
    tag.get.id should be(tagId)
    tag.get.name should be(tagName)

    // проверка удаления тега от записи
    Await.result(client.removeTagFromEntry(entryId, entryId))
    Await.result(client.getEntryTags(entryId)).find(_.id == tagId) should be(None)

    // удаление записи и тега
    Await.result(client.removeTag(tagId))
    Await.result(client.removeEntry(entryId))
  }

  "Service" should "get entry tag list" in {
    val entryName = "entry"
    val firstTagName = "first tag"
    val secondTagName = "second tag"
    val thirdTagName = "third tag"

    // добавление записи и трех тегов
    val entryId = Await.result(client.addEntry(entryName))
    val firstTagId = Await.result(client.addTag(firstTagName))
    val secondTagId = Await.result(client.addTag(secondTagName))
    val thirdTagId = Await.result(client.addTag(thirdTagName))

    // добавление тегов к записи
    Await.result(client.addTagToEntry(entryId, firstTagId))
    Await.result(client.addTagToEntry(entryId, secondTagId))
    Await.result(client.addTagToEntry(entryId, thirdTagId))

    // получение всех тегов записи
    val entryTags = Await.result(client.getEntryTags(entryId))

    // поиск добавленных тегов в записи тегов
    val firstTag = entryTags.find(_.id == firstTagId)
    val secondTag = entryTags.find(_.id == secondTagId)
    val thirdTag = entryTags.find(_.id == thirdTagId)

    // проверка найденных тегов
    firstTag should be('defined)
    firstTag.get.id should be(firstTagId)
    firstTag.get.name should be(firstTagName)
    secondTag should be('defined)
    secondTag.get.id should be(secondTagId)
    secondTag.get.name should be(secondTagName)
    thirdTag should be('defined)
    thirdTag.get.id should be(thirdTagId)
    thirdTag.get.name should be(thirdTagName)

    // удаление записей, тегов и связок
    Await.result(client.removeTagFromEntry(entryId, firstTagId))
    Await.result(client.removeTagFromEntry(entryId, secondTagId))
    Await.result(client.removeTagFromEntry(entryId, thirdTagId))
    Await.result(client.removeTag(firstTagId))
    Await.result(client.removeTag(secondTagId))
    Await.result(client.removeTag(thirdTagId))
    Await.result(client.removeEntry(entryId))
  }

  "Service" should "get entries by tag list" in {
    val firstEntryName = "first entry"
    val secondEntryName = "second entry"
    val thirdEntryName = "third entry"
    val firstTagName = "first tag"
    val secondTagName = "second tag"
    val thirdTagName = "third tag"

    // добавление записей и тегов
    val firstEntryId = Await.result(client.addEntry(firstEntryName))
    val secondEntryId = Await.result(client.addEntry(secondEntryName))
    val thirdEntryId = Await.result(client.addEntry(thirdEntryName))
    val firstTagId = Await.result(client.addTag(firstTagName))
    val secondTagId = Await.result(client.addTag(secondTagName))
    val thirdTagId = Await.result(client.addTag(thirdTagName))

    // добавление тегов к записям
    Await.result(client.addTagToEntry(firstEntryId, firstTagId))
    Await.result(client.addTagToEntry(firstEntryId, thirdTagId))
    Await.result(client.addTagToEntry(secondEntryId, secondTagId))
    Await.result(client.addTagToEntry(thirdEntryId, secondTagId))
    Await.result(client.addTagToEntry(thirdEntryId, thirdTagId))

    // получение всех записей с первым и третьим тегами
    val entries = Await.result(client.getEntriesByTags(List(firstTagId, thirdTagId)))

    // проверка кол-ва полученных записей
    entries.length should be(2)

    // определение записей
    val firstEntry = entries.find(_.id == firstEntryId)
    val secondEntry = entries.find(_.id == secondEntryId)
    val thirdEntry = entries.find(_.id == thirdEntryId)

    // проверка найденных записей
    firstEntry should be('defined)
    firstEntry.get.id should be(firstEntryId)
    firstEntry.get.name should be(firstEntryName)
    secondEntry should be(None)
    thirdEntry should be('defined)
    thirdEntry.get.id should be(thirdEntryId)
    thirdEntry.get.name should be(thirdEntryName)

    // удаление записей, тегов и связок
    Await.result(client.removeTagFromEntry(firstEntryId, firstTagId))
    Await.result(client.removeTagFromEntry(firstEntryId, thirdTagId))
    Await.result(client.removeTagFromEntry(secondEntryId, secondTagId))
    Await.result(client.removeTagFromEntry(thirdEntryId, secondTagId))
    Await.result(client.removeTagFromEntry(thirdEntryId, thirdTagId))
    Await.result(client.removeTag(firstTagId))
    Await.result(client.removeTag(secondTagId))
    Await.result(client.removeTag(thirdTagId))
    Await.result(client.removeEntry(firstEntryId))
    Await.result(client.removeEntry(secondEntryId))
    Await.result(client.removeEntry(thirdEntryId))
  }
}
