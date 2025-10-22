package com.collaborative_task_tracking_system.Collaborative.Task.Tracking.System.controller;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/stream/{userId}")
    public SseEmitter streamNotifications(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);
        emitter.onCompletion(() -> emitters.remove(userId));
        return emitter;
    }

    @PostMapping("/send/{userId}")
    public ResponseEntity<Notification> sendNotification(@PathVariable String userId, @RequestBody String message) {
        Notification n = notificationService.createNotification(userId, message);
        if (emitters.containsKey(userId)) {
            try {
                emitters.get(userId).send(SseEmitter.event().data(n));
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
        return ResponseEntity.ok(n);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(notificationService.getNotifications(userId));
    }
}