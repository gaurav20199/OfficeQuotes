async function fetchLeaderboardData() {
    try {
        const response = await fetch('/quiz/leaderboard/data');
        if (!response.ok) {
            throw new Error('Failed to fetch leaderboard data');
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching leaderboard data:', error);
        // Show a user-friendly error message in the table
        const leaderboardBody = document.getElementById('leaderboardBody');
        leaderboardBody.innerHTML = `
            <tr>
                <td colspan="3" class="text-center py-4">
                    <p class="text-danger mb-0">Unable to load leaderboard data. Please try again later.</p>
                </td>
            </tr>
        `;
        return [];
    }
}

async function populateLeaderboard() {
    const leaderboardBody = document.getElementById('leaderboardBody');

    // Show loading state
    leaderboardBody.innerHTML = `
        <tr>
            <td colspan="3" class="text-center py-4">
                <div class="spinner-border text-primary" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </td>
        </tr>
    `;

    const leaderboardData = await fetchLeaderboardData();

    // Clear loading state
    leaderboardBody.innerHTML = '';

    leaderboardData.forEach(player => {
        const row = document.createElement('tr');

        // Create rank cell with badge
        const rankCell = document.createElement('td');
        rankCell.className = 'text-center';
        const rankBadge = document.createElement('div');
        rankBadge.className = `rank-badge ${player.rank <= 3 ? 'rank-' + player.rank : 'bg-secondary'}`;
        rankBadge.textContent = player.rank;
        rankCell.appendChild(rankBadge);

        // Create username cell
        const usernameCell = document.createElement('td');
        usernameCell.className = 'player-name';
        usernameCell.textContent = player.userName;

        // Create score cell
        const scoreCell = document.createElement('td');
        scoreCell.className = 'text-end score';
        scoreCell.textContent = player.score;

        // Append cells to row
        row.appendChild(rankCell);
        row.appendChild(usernameCell);
        row.appendChild(scoreCell);

        // Append row to table body
        leaderboardBody.appendChild(row);
    });
}

// Initialize the leaderboard when the page loads
document.addEventListener('DOMContentLoaded', populateLeaderboard);