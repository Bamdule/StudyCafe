class JwtUtils {
    constructor() {
    }

    checkExpiration(token) {
        if (token === null) return true;

        let payload = this.parseJWT(token);

        let expiration = payload.exp < (new Date().getTime() + 1) / 1000;

        if (expiration) {
            showToast("로그인이 만료되었습니다.");
        }

        return expiration;
    }

    parseJWT(token) {
        try {
            return JSON.parse(atob(token.split('.')[1]));
        } catch (e) {
            return null;
        }
    };
}