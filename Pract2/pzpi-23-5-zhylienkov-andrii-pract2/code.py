1  import redis
2  from db import CassandraDB
3
4  class FeedService:
5      def get_user_feed(self, user_id, limit=20):
6          # 1. Спроба отримати дані з кешу (Redis) — швидкий шлях
7          cache_key = f"feed:{user_id}"
8          cached_feed = self.cache.get(cache_key)
9
10         if cached_feed:
11             return json.loads(cached_feed)
12
13         # 2. Cache Miss: Запит до основної бази (Cassandra)
14         # Використовуємо fan-out read pattern
15         feed_data = self.db.query(
16             "SELECT * FROM user_feeds WHERE user_id = %s LIMIT %s",
17             (user_id, limit)
18         )
19
20         # 3. Оновлення кешу (Write-through або Async)
21         if feed_data:
22             self.cache.set(cache_key, json.dumps(feed_data), ttl=60)
23
24         return feed_data
25
26     def _trigger_async_prefetch(self, user_id):
27         # Відправка події в RabbitMQ для попереднього завантаження
28         self.queue.publish("feed_prefetch", {"user_id": user_id})