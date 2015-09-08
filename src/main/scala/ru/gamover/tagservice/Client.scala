package ru.gamover.tagservice

import com.twitter.finagle.Thrift
import com.twitter.util.Future
import ru.gamover.tagservice.thriftscala.TagService

/**
 * Created by G☠mOBEP [ 07.09.15 ]
 */

/**
 * Клиент для работы с thrift-сервисом
 */
class Client() {
  import ru.gamover.tagservice.Storage._

  val client = Thrift.newIface[TagService.FutureIface]("localhost:8080")

  /**
   * Добавление записи
   * @param name имя записи
   * @return
   */
  def addEntry(name: String): Future[Long] = client.addEntry(name)

  /**
   * Удаление записи
   * @param id идентификатор записи
   * @return
   */
  def removeEntry(id: Long): Future[Unit] = client.removeEntry(id)

  /**
   * Получение записи по идентификтору
   * @param id идентификатор записи
   * @return
   */
  def getEntryById(id: Long): Future[Option[Entry]] = {
    client.getEntryById(id) map { (result: Seq[thriftscala.Entry]) =>
      if (result.nonEmpty) Some(result.toList.head)
      else None
    }
  }

  /**
   * Получение списка записей по списку идентификаторов тегов
   * @param tagIdList список идентификаторов тегов
   * @return
   */
  def getEntriesByTags(tagIdList: List[Long]): Future[List[Entry]] = {
    client.getEntriesByTags(tagIdList) map { (result: Seq[thriftscala.Entry]) =>
      result.toList
    }
  }

  /**
   * Добавление тега
   * @param name имя тега
   * @return
   */
  def addTag(name: String): Future[Long] = client.addTag(name)

  /**
   * Удаление тега
   * @param id идентификатор тега
   * @return
   */
  def removeTag(id: Long): Future[Unit] = client.removeTag(id)

  /**
   * Получение тега по идентификатору
   * @param id идентификатор тега
   * @return
   */
  def getTagById(id: Long): Future[Option[Tag]] = {
    client.getTagById(id) map { (result: Seq[thriftscala.Tag]) =>
      if (result.nonEmpty) Some(result.toList.head)
      else None
    }
  }

  /**
   * Получение списка тегов записи
   * @param entryId идентификатор записи
   * @return
   */
  def getEntryTags(entryId: Long): Future[List[Tag]] = {
    client.getEntryTags(entryId) map { (result: Seq[thriftscala.Tag]) =>
      result.toList
    }
  }

  /**
   * Добавление тега к записи
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   * @return
   */
  def addTagToEntry(entryId: Long, tagId: Long): Future[Long] = client.addTagToEntry(entryId, tagId)

  /**
   * Удаление тега от записи
   * @param entryId идентификатор записи
   * @param tagId   идентификатор тега
   * @return
   */
  def removeTagFromEntry(entryId: Long, tagId: Long): Future[Unit] = client.removeTagFromEntry(entryId: Long, tagId: Long)
}