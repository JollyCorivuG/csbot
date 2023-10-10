export const format = (time: string): string => {
    const now: Date = new Date()
    const t: Date = new Date(time)
    if (now.getFullYear() == t.getFullYear() && now.getMonth() == t.getMonth() && now.getDate() == t.getDate()) {
        const hour: string = t.getHours() < 10 ? `0${t.getHours()}` : `${t.getHours()}`
        const minute: string = t.getMinutes() < 10 ? `0${t.getMinutes()}` : `${t.getMinutes()}`
        return `${hour}:${minute}`
    }
    const yesterday: Date = new Date(now.getTime() - 24 * 60 * 60 * 1000)
    if (yesterday.getFullYear() == t.getFullYear() && yesterday.getMonth() == t.getMonth() && yesterday.getDate() == t.getDate()) {
        const hour: string = t.getHours() < 10 ? `0${t.getHours()}` : `${t.getHours()}`
        const minute: string = t.getMinutes() < 10 ? `0${t.getMinutes()}` : `${t.getMinutes()}`
        return `昨天 ${hour}:${minute}`
    }
    const hour: string = t.getHours() < 10 ? `0${t.getHours()}` : `${t.getHours()}`
    const minute: string = t.getMinutes() < 10 ? `0${t.getMinutes()}` : `${t.getMinutes()}`
    return `${t.getMonth() + 1}月${t.getDate()}日 ${hour}:${minute}`
}

export const isInSameMinute = (t1: string, t2: string): boolean => {
    const d1: Date = new Date(t1)
    const d2: Date = new Date(t2)
    return d1.getFullYear() == d2.getFullYear() && d1.getMonth() == d2.getMonth() && d1.getDate() == d2.getDate() && d1.getHours() == d2.getHours() && d1.getMinutes() == d2.getMinutes()
}
